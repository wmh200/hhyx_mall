package hust.wang.hhyx.service.impl;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hust.wang.hhyx.constant.OrderConstant;
import hust.wang.hhyx.emun.OrderStatusEnum;
import hust.wang.hhyx.entity.cart.vo.CartItem;
import hust.wang.hhyx.entity.member.MemberDelivery;
import hust.wang.hhyx.entity.member.vo.MemberPositionVo;
import hust.wang.hhyx.entity.member.vo.MemberVo;
import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.entity.order.OrderItem;
import hust.wang.hhyx.entity.order.vo.OrderCreateVo;
import hust.wang.hhyx.entity.order.vo.OrderInfoVo;
import hust.wang.hhyx.entity.order.vo.OrderRespVo;
import hust.wang.hhyx.entity.order.vo.OrderSubmitVo;
import hust.wang.hhyx.entity.product.Good;
import hust.wang.hhyx.entity.seckill.vo.SeckillOrderVo;
import hust.wang.hhyx.entity.shopkeeper.ShopKeeper;
import hust.wang.hhyx.entity.stock.vo.StockSkuLockVo;
import hust.wang.hhyx.exception.NoStockException;
import hust.wang.hhyx.feign.CartFeignService;
import hust.wang.hhyx.feign.GoodFeignService;
import hust.wang.hhyx.feign.MemberFeignService;
import hust.wang.hhyx.feign.StockFeignService;
import hust.wang.hhyx.mapper.OrderMapper;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.OrderItemService;
import hust.wang.hhyx.service.OrderService;
import hust.wang.hhyx.util.RedisUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 同一个对象内事务方法互调默认失效 原因 绕过了代理对象 事务使用代理对象来控制的
 * 解决: 使用代理对象来调用事务方法
 * 1）.引入aop   spring-boot-starter-aop 引入了aspectj、
 * 2）.@EnableAspectJAutoProxy(exposeProxy = true):开启aspectj动态代理功能 以后所有的动态代理都是
 *     aspectj创建的(即使没有接口也会创建动态代理)  对外暴露代理对象
 *
 * 3).用代理对象
 *
 *         OrderServiceImpl o  = (OrderServiceImpl) AopContext.currentProxy();
 *         o.b();
 *         o.c();
 *
 * Seata控制分布式事务
 *  1) 每一个微服务先必须创建 undo_log
 *  2) 安装事务协调器 seata—server
 *  3) 整合
 *     1.导入依赖 spring-cloud-starter-alibaba-seata seata-all-0.7.1可能会出现jar包依赖问题 要排除
 *     2.启动seata-server：
 *       register.conf:注册中心配置；修改register type=nacos
 *       file.conf:
 *     3.所有想要用分布式事务的微服务使用seata DataSourceProxy代理自己的数据源
 *     4. 每个我服务都导入
 *         register.conf
 *         file.conf  vgroup_mapping.hhyx-order-fescar-service-group = "default"
 *     5. 启动测试分布式事务
 *     6. 给每个分布式大事务的入口标注 @GlobalTransactional
 *     7. 每个远程小事务用@Transactional
 *
 *     //这个是 0.7.1版本  1.4.0版本看收藏的博客
 *
 * 问题：不支持高并发。。。。
 *
 *
 *
 */

/**
 * @Author wangmh
 * @Date 2021/1/22 9:39 下午
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private ThreadLocal<OrderSubmitVo>  threadLocal = new ThreadLocal<>();

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CartFeignService cartFeignService;

    @Autowired
    GoodFeignService goodFeignService;
    
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    StockFeignService stockFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 关闭订单
     */
    @Override
    public void closeOrder(Order order){
        //查询这个订单的最新状态
        Order orderEntity = orderMapper.selectById(order.getId());
        if (orderEntity.getStatus() == OrderStatusEnum.CREATE_NEW.getCode()){
            orderEntity.setStatus(OrderStatusEnum.CANCELED.getCode());
            orderMapper.updateById(orderEntity);
            //发给MQ一个 TODO 保证消息百分百发出去，每一个消息都做好日志记录(给数据库保存每一个消息的详细信息)。
            // TODO 定期扫描数据库 将失败的消息再发送
            rabbitTemplate.convertAndSend("order-event-exchange","order.release.other",orderEntity);
        }
    }

    /**
     * 获取用户的订单列表
     * @param request
     * @return
     */
    @Override
    public List<OrderInfoVo> getMemberOrders(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken").replace("[", "").replace("]", "");
        String s = redisUtil.get(accessToken);
        String[] split = redisUtil.get(accessToken).split("-");
        String nickname = split[0];
        String phone = split[1];
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_username",nickname);
        queryWrapper.eq("reciever_phone",phone);
        queryWrapper.last("order by create_time desc");
        List<Order> orders = orderMapper.selectList(queryWrapper);
        List<OrderInfoVo> orderInfoVoList = new ArrayList<>();
        for (Order order: orders) {
            String orderSn = order.getOrderSn();
            List<OrderItem> items = orderItemService.selectList(orderSn);
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            orderInfoVo.setOrder(order);
            orderInfoVo.setOrderItemList(items);
            orderInfoVoList.add(orderInfoVo);
        }
        return orderInfoVoList;
    }

    /**
     * 支付成功保存订单信息 TODO 后面如果做真正的支付功能需要整合微信或者支付宝的支付 需要验签 结合第三方支付 等等。。。
     * @param order
     */
    @Override
    public String paySuccess(Order order) {
        order.setPaymentTime(new Date());
        //微信支付
        order.setPayType(1);
        order.setUseBalance(0.0);
        order.setStatus(OrderStatusEnum.SEND_NEED.getCode());
        int i = orderMapper.updateById(order);
        if (i == 1){
            return order.getOrderSn();
        }else{
            return null;
        }
    }

    /**
     * 删除订单(逻辑删除)
     * @param orderId
     */
    @Override
    public void deleteOrder(String orderId) {
        Order order = orderMapper.selectById(orderId);
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status",4);
        updateWrapper.set("delete_status",1);
        updateWrapper.set("modify_time",new Date());
        orderMapper.update(order,updateWrapper);
    }

    /**
     * 根据订单id查询订单信息
     * @param orderId
     * @return
     */
    @Override
    public OrderRespVo getOrderInfoById(String orderId) {
        Order order = orderMapper.selectById(orderId);
        String orderSn = order.getOrderSn();
        List<OrderItem> items = orderItemService.selectList(orderSn);
        OrderRespVo orderRespVo = new OrderRespVo();
        orderRespVo.setStatus(4);
        orderRespVo.setOrderEntity(order);
        orderRespVo.setItemList(items);
        return orderRespVo;
    }

    /**
     * 创建秒杀单的详细信息
     * @param seckillOrderVo
     */
    @Override
    public void createSeckillOrder(SeckillOrderVo seckillOrderVo) {
       //保存订单信息
        Order order = new Order();
        order.setCreateTime(new Date());

        order.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        order.setOrderSn(seckillOrderVo.getOrderSn());
        order.setDeleteStatus(0);
        order.setPayType(1);

        order.setPromotionAmount(0.0);
        order.setPayAmount(seckillOrderVo.getSeckillPrice()*seckillOrderVo.getNum());
        order.setMemberOpenid(seckillOrderVo.getOpenId());
        //TODO 后面的一些信息要改接口 前端传很多订单信息过来。。。有时间再修改
        orderMapper.insert(order);

        //TODO 保存订单项信息
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderSn(seckillOrderVo.getOrderSn());
        orderItem.setRealAmount(seckillOrderVo.getSeckillPrice()*seckillOrderVo.getNum());
        orderItem.setSguPrice(seckillOrderVo.getSeckillPrice());
        orderItem.setSguId(seckillOrderVo.getSguId());
        orderItemService.save(orderItem);


    }

    @Override
    @Transactional
    //在本地事务，在分布式系统，只能控制住自己的回滚，控制不了其他服务的回滚
    //分布式事务：最大问题。网络问题+分布式机器

    //@Transactional(propagation = Propagation.REQUIRED) 共用一个事务
    //(propagation = Propagation.REQUIRES_NEW) 新开一个事务 但是在spring @Service 一个
    // Service下 这些设置没有o用
//    @GlobalTransactional
    /**
     * 提交订单
     */
    public OrderRespVo submitOrder(OrderSubmitVo orderSubmitVo) {
        threadLocal.set(orderSubmitVo);
        OrderRespVo orderRespVo = new OrderRespVo();
        String openId = orderSubmitVo.getUserInfo().getOpenId();
        // 1.验证令牌【令牌的对比和删除必须保证原子性】
        // 返回值 0代表校验失败 -1删除成功
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        String orderToken = orderSubmitVo.getOrderToken();
        Long result = redisUtil.execute(script, OrderConstant.USER_ORDER_TOKEN_PREFIX + openId, orderToken);
        if (result == 1){
            // 令牌验证成功
            // 1.创建订单、订单项、用户自提点信息等信息
            OrderCreateVo orderCreateVo = createOrder();
            // 2.验价
            Double payPrice = orderCreateVo.getPayPrice();
            Double payAmount = orderSubmitVo.getPayAmount();
            if(Math.abs(payPrice-payAmount)<0.01) {
                // 金额对比成功后
                //3.保存订单、订单项、用户自提点信息数据
                saveOrderCreateInfo(orderCreateVo);
                //4.库存锁定。只要有异常 回滚订单数据
                StockSkuLockVo stockSkuLockVo = new StockSkuLockVo();
                stockSkuLockVo.setOrderSn(orderCreateVo.getOrder().getOrderSn());
                stockSkuLockVo.setOrderItems(orderCreateVo.getOrderItems());
                   //远程锁库存
                R r = stockFeignService.orderLockStock(stockSkuLockVo);
                if (r.getCode() == 20000) {
                    //锁库存成功
                    orderRespVo.setStatus(1);
                    orderRespVo.setOrderEntity(orderCreateVo.getOrder());
                    orderRespVo.setItemList(orderCreateVo.getOrderItems());
                    // 测试回滚
//                    int i = 10 / 0;
                    // TODO 订单创建成功发送消息给MQ
                    rabbitTemplate.convertAndSend("order-event-exchange","order.create.order",orderCreateVo.getOrder());
                    return orderRespVo;
                }else{
                    String msg = r.getMsg();
                    throw new NoStockException(msg);
//                    orderRespVo.setStatus(3);
//                    return orderRespVo;
                }
            }else{
                orderRespVo.setStatus(2);
                return orderRespVo;
            }

        }
        // 过时了 防盗刷
        orderRespVo.setStatus(4);
        return orderRespVo;
    }

    /**
     * 按订单号来查询订单
     * @param orderSn
     * @return
     */
    @Override
    public Order getOrderByOrderSn(String orderSn) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_sn",orderSn);
        Order order = orderMapper.selectOne(queryWrapper);
        return order;
    }

    /**
     * 保存订单及相关信息
     * @param orderCreateVo
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderCreateInfo(OrderCreateVo orderCreateVo) {
        Order order = orderCreateVo.getOrder();
        order.setCreateTime(new Date());
        orderMapper.insert(order);
        List<OrderItem> orderItems = orderCreateVo.getOrderItems();
        MemberDelivery memberDelivery = orderCreateVo.getMemberDelivery();
        memberFeignService.addMemberDelivery(memberDelivery);
        orderItemService.insertItemList(orderItems);
    }

    /**
     * 创建订单
     * @return
     */
    public OrderCreateVo createOrder(){
        OrderCreateVo orderCreateVo = new OrderCreateVo();
        //1.生成订单号、创建订单
        String orderSn = IdWorker.getTimeId();
            //构建订单
        Order order = buildOrder(orderSn);

        //2.获取到所有的订单项
        List<OrderItem> orderItemList = buildOrderItems(orderSn);

        //3.计算、设置价格
        order = computePrice(order, orderItemList);

        //4.获取用户自提点信息
        MemberDelivery memberDelivery = buildMemberDelivery();

        orderCreateVo.setPayPrice(order.getPayAmount());
        orderCreateVo.setOrder(order);
        orderCreateVo.setOrderItems(orderItemList);
        orderCreateVo.setMemberDelivery(memberDelivery);
        return orderCreateVo;
    }

    /**
     * 构建用户自提点信息
     * @return
     */
    private MemberDelivery buildMemberDelivery() {
        OrderSubmitVo orderSubmitVo = threadLocal.get();
        ShopKeeper shopKeeper = orderSubmitVo.getShopkeeper();
        MemberPositionVo position = orderSubmitVo.getPosition();
        MemberVo userInfo = orderSubmitVo.getUserInfo();
        MemberDelivery memberDelivery = new MemberDelivery();
        memberDelivery.setMemberAddress(position.getAddress());
        memberDelivery.setMemberProvince(position.getProvince());
        memberDelivery.setMemberCity(position.getCity());
        memberDelivery.setMemberDistrict(position.getDistrict());
        memberDelivery.setStoreId(shopKeeper.getStoreId());
        memberDelivery.setMemberPhone(userInfo.getPhone());
        memberDelivery.setMemberOpenId(userInfo.getOpenId());
        return memberDelivery;
    }

    /**
     * 计算、设置价格
     * @param order
     * @param orderItemList
     * @return
     */
    private Order computePrice(Order order, List<OrderItem> orderItemList) {
        double total = 0.0;
        double pay = 0.0;
        double promotion = 0.0;
        double coupon = 0.0;
        for (OrderItem orderItem : orderItemList){
            total += orderItem.getSguPrice()*orderItem.getSguCount();
            pay += orderItem.getRealAmount()*orderItem.getSguCount();
            promotion += orderItem.getPromotionAmount()*orderItem.getSguCount();
            coupon += orderItem.getCouponAmount()*orderItem.getSguCount();
        }
        //设置订单价格相关
        order.setPayAmount(pay);
        order.setTotalAmount(total);
        order.setPromotionAmount(promotion);
        order.setCouponAmount(coupon);
        return order;
    }

    /**
     * 构建订单基本信息
     * @param orderSn
     * @return
     */
    private Order buildOrder(String orderSn) {
        Order order = new Order();
        order.setOrderSn(orderSn);

        order.setDeleteStatus(0);

        OrderSubmitVo orderSubmitVo = threadLocal.get();
        //1.获取团长信息
        ShopKeeper shopKeeper = orderSubmitVo.getShopkeeper();
        order.setKeeperId(shopKeeper.getStoreId());
        order.setKeeperName(shopKeeper.getNickname());
        order.setKeeperAddress(shopKeeper.getAddress());
        order.setKeeperPhone(shopKeeper.getResidentialPhone());

        //2.获取用户信息
        MemberPositionVo position = orderSubmitVo.getPosition();
        MemberVo userInfo = orderSubmitVo.getUserInfo();
        order.setMemberUsername(userInfo.getNickName());
        order.setRecieverProvince(position.getProvince());
        order.setRecieverCity(position.getCity());
        order.setRecieverDetailAddress(position.getAddress());
        order.setRecieverName(userInfo.getNickName());
        order.setRecieverPhone(userInfo.getPhone());
        order.setRecieverRegion(position.getDistrict());
        order.setSourceType(1);

        //3.获取用户的openid
        order.setMemberOpenid(userInfo.getOpenId());

        //4.设置订单状态 未付款
        order.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        order.setAutoConfirmDay(7);
        return order;


    }

    /**
     * 构建订单项
     * @param orderSn
     * @return
     */
    private List<OrderItem> buildOrderItems(String orderSn) {
        R cart = cartFeignService.getCart();
        String carts = JSON.toJSONString(cart.getData().get("cartList"));
        List<CartItem> cartList = JSON.parseArray(carts, CartItem.class);
        List<OrderItem> result = new ArrayList<>();
        cartList.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderSn(orderSn);
            orderItem.setSguId(cartItem.getSguId());
            orderItem.setSguName(cartItem.getSguName());
            orderItem.setSguCount(cartItem.getCount());
            orderItem.setSguPic(cartItem.getBigImageUrl());
            orderItem.setSguPrice(cartItem.getSalePrice());

            R r = goodFeignService.getGoodBySguId(cartItem.getSguId());
            Good good = JSON.parseObject(r.getData().get("good").toString(), Good.class);
            orderItem.setCategoryId(good.getCategoryId());
            orderItem.setCategoryName(good.getCategoryName());
            orderItem.setCouponAmount(0.0);
            orderItem.setPromotionAmount(0.0);
            orderItem.setRealAmount(cartItem.getSalePrice());
            result.add(orderItem);
        });
        return result;
    }


}
