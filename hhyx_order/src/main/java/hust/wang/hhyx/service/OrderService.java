package hust.wang.hhyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.entity.order.vo.OrderInfoVo;
import hust.wang.hhyx.entity.order.vo.OrderRespVo;
import hust.wang.hhyx.entity.order.vo.OrderSubmitVo;
import hust.wang.hhyx.entity.seckill.vo.SeckillOrderVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/22 9:39 下午
 **/
public interface OrderService extends IService<Order> {
    OrderRespVo submitOrder(OrderSubmitVo orderSubmitVo);
    
    Order getOrderByOrderSn(String orderSn);

    void closeOrder(Order order);

    List<OrderInfoVo> getMemberOrders(HttpServletRequest request);

    String paySuccess(Order order);

    void deleteOrder(String orderId);

    OrderRespVo getOrderInfoById(String orderId);

    void createSeckillOrder(SeckillOrderVo seckillOrderVo);
}
