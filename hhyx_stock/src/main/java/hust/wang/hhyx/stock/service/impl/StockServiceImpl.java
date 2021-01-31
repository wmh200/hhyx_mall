package hust.wang.hhyx.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hust.wang.hhyx.entity.mq.stock.StockDetailTo;
import hust.wang.hhyx.entity.mq.stock.StockLockedTo;
import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.entity.order.OrderItem;
import hust.wang.hhyx.entity.product.Good;
import hust.wang.hhyx.entity.stock.Stock;
import hust.wang.hhyx.entity.stock.StockDetail;
import hust.wang.hhyx.entity.stock.StockTask;
import hust.wang.hhyx.entity.stock.vo.StockSkuLockVo;
import hust.wang.hhyx.entity.stock.vo.StockVo;
import hust.wang.hhyx.exception.NoStockException;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.stock.feign.OrderFeign;
import hust.wang.hhyx.stock.feign.ProductFeign;
import hust.wang.hhyx.stock.mapper.StockMapper;
import hust.wang.hhyx.stock.service.StockDetailService;
import hust.wang.hhyx.stock.service.StockService;
import hust.wang.hhyx.stock.service.StockTaskService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
/**
 * @Author wangmh
 * @Date 2021/1/24 8:55 上午
 **/
@Service
@RabbitListener(queues = "stock.release.stock.queue")
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    StockMapper stockMapper;

    @Autowired
    ProductFeign productFeign;

    @Autowired
    StockDetailService stockDetailService;

    @Autowired
    StockTaskService stockTaskService;

    @Autowired
    OrderFeign orderFeign;



    @Override
    /**
     * 库存解锁
     */
    public void unlockStock(StockLockedTo stockLockedTo) {
        StockDetailTo detail = stockLockedTo.getDetail();
        String sguId = detail.getSguId();
        int detailId = detail.getId();
        //解锁
        //1 查询数据库关于这个订单的锁定库存信息
        // 有 ：证明库存锁定成功
        //     解锁 ：订单情况。
        //        1.没有这个订单 不需要解锁
        //         2.有这个订单。 不是解锁库存。
        //                订单状态： 已取消: 解锁库存
        //                          没取消：不能解锁
        // 没有 : 锁定库存失败了，库存回滚了。无需解锁
        StockDetail stockDetail = stockDetailService.getById(detailId);
        if (stockDetail != null){
            //解锁
            int id = stockLockedTo.getId();
            StockTask byId = stockTaskService.getById(id);
            String orderSn = byId.getOrderSn();
            R orderByOrderSn = orderFeign.getOrderByOrderSn(orderSn);
            if (orderByOrderSn.getCode() == 20000) {
                // 1.订单不存在
                if (orderByOrderSn.getData().get("order") == null) {
                    //是未解锁的状态才解锁
                    if (stockDetail.getLockStatus() == 1){
                        //锁定库存
                        unLockStock(sguId,stockDetail.getSguNum(),detailId);
                        return;
                    }
                }
                System.out.println(orderByOrderSn.getData().get("order"));
                Order order = JSONObject.parseObject(orderByOrderSn.getData().get("order").toString(), Order.class);
                // 2.订单存在 被取消
                if (order.getStatus() == 4) {
                    // 订单已经被取消。才能解锁库存
                    if (stockDetail.getLockStatus() == 1){
                        unLockStock(sguId,stockDetail.getSguNum(),detailId);
                        return;
                    }
                }
            }else{
                // 消息拒绝以后重新放到队列 让别人继续消费解锁。
                throw new RuntimeException("远程服务失败");
            }
        }else{
            //无需解锁
        }
    }

    /**
     * 防止订单服务卡顿导致订单状态消息一直改不了，库存消息优先到期。查订单状态是新建状态 什么都不做就走了
     * 导致卡顿的订单，永远不能解锁库存
     * @param order
     */
    @Override
    public void unlockStock(Order order) {
        String orderSn = order.getOrderSn();
        StockTask stockTask = stockTaskService.selectBySn(orderSn);
        int taskId = stockTask.getId();
        List<StockDetail> stockDetails = stockDetailService.list(
                               new QueryWrapper<StockDetail>()
                                   .eq("task_id", taskId)
                                       .eq("lock_status", 1));
        for (StockDetail stockDetail: stockDetails){
            unLockStock(stockDetail.getSguId(),stockDetail.getSguNum(),stockDetail.getId());
        }

    }

    @Override
    public Stock getSguStock(String sguId) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sgu_id",sguId);
        Stock stock = stockMapper.selectOne(queryWrapper);
        return stock;
    }

    /**
     * 解锁库存--具体操作
     * @param skuId 商品id
     * @param num  购买量
     */
    private void unLockStock(String skuId,int num,int detailId){
        stockMapper.updateStock(skuId,num);
        // xiu
        UpdateWrapper<StockDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("lock_status",2);
        updateWrapper.eq("id",detailId);
        stockDetailService.update(updateWrapper);
    }

    @Override
    @Transactional
    public Boolean orderLockStock(StockSkuLockVo stockSkuLockVo) {
        /**
         * 保存库存工作单的详情
         * 追溯。
         */
        StockTask stockTask = new StockTask();
        stockTask.setOrderSn(stockSkuLockVo.getOrderSn());
        stockTaskService.save(stockTask);


        List<OrderItem> orderItems = stockSkuLockVo.getOrderItems();
        orderItems.stream().forEach(item -> {
            String skuId = item.getSguId();
            Integer sguCount = item.getSguCount();
            Stock stock = queryStock(skuId, sguCount);
        /**
         * 1.如果每一个商品都锁定成功，将当前商品锁定了几件的工作单记录发给MQ
         *
         * 2.如果锁定失败。前面保存的工作单信息就回滚了。发送出去的消息，即使要解锁记录
         *   由于去数据库查不到id，所以就不用解锁
         *
         *
         */
            if (stock != null) {
                stock.setSguStock(stock.getSguStock() - sguCount);
                stock.setSguDelivery(stock.getSguDelivery() + sguCount);
                stockMapper.updateById(stock);
                StockDetail stockDetail = new StockDetail(null, item.getSguId(), item.getSguName(), item.getSguCount(), stockTask.getId(), 1);
                //保存任务详情信息
                stockDetailService.save(stockDetail);
                //发送到延时队列中
                StockLockedTo stockLockedTo = new StockLockedTo();
                stockLockedTo.setId(stockTask.getId());
                StockDetailTo stockDetailTo = new StockDetailTo();
                BeanUtils.copyProperties(stockDetail,stockDetailTo);
                //防止回滚后找不到数据
                stockLockedTo.setDetail(stockDetailTo);
                rabbitTemplate.convertAndSend("stock-event-exchange","stock.locked",stockLockedTo);
            } else {
                throw new NoStockException(skuId);
            }
        });
        return true;
    }

    @Override
    public void updateStock(String skuId, int num) {
        stockMapper.updateStock(skuId,num);
    }

    /**
     * 查询库存--操作数据库
     * @param skuId
     * @param sguCount
     * @return
     */
    private Stock queryStock(String skuId,int sguCount) {
        Stock stock = stockMapper.selectOneByParams(skuId,sguCount);
        return stock;
    }

    /**
     * 初始化库存信息
     */
    @Override
    public void insertStocks() {
        R stockInfo = productFeign.getStockInfo();
        String str = stockInfo.getData().get("stockInfo").toString();
        List<Good> list = JSON.parseArray(str,Good.class);
        List<Stock> result = new ArrayList<>();
        List<String> sguIds = new ArrayList<>();
        list.stream().forEach(good -> {
            if(!sguIds.contains(good.getSguId())){
                StockVo stockVo = new StockVo();
                Stock stock = new Stock();
                stockVo.setSguId(good.getSguId());
                stockVo.setSguName(good.getSguName());
                stockVo.setSguStock(good.getSellableStock());
                stockVo.setSguSale(good.getTotalSales());
                BeanUtils.copyProperties(stockVo,stock);
                stock.setSguTotal(stock.getSguSale()+stock.getSguStock());
                result.add(stock);
                sguIds.add(good.getSguId());
            }
        });
        result.forEach(stock -> {
            stockMapper.insert(stock);
        });
    }
}
