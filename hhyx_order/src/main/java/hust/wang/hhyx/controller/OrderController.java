package hust.wang.hhyx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hust.wang.hhyx.constant.OrderConstant;
import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.entity.order.vo.OrderInfoVo;
import hust.wang.hhyx.entity.order.vo.OrderRespVo;
import hust.wang.hhyx.entity.order.vo.OrderSubmitVo;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.OrderService;
import hust.wang.hhyx.util.RedisUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @Author wangmh
 * @Date 2021/1/20 2:57 下午
 **/
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    OrderService orderService;

    /**
     * 产生token 保证幂等性
     */
    @PostMapping("/generateToken")
    public R generateToken(@RequestParam(value = "openId")String openId){
        String orderToken = UUID.randomUUID().toString().replace("_","");
        redisUtil.setEx(OrderConstant.USER_ORDER_TOKEN_PREFIX+openId,orderToken,2000, TimeUnit.MINUTES);
        return R.ok().data("orderToken",orderToken);
    }
    /**
     *  提交订单
     * @param orderSubmitVo
     * @return
     */
    @PostMapping("/submitOrder")
    public R submitOrder(@RequestBody OrderSubmitVo orderSubmitVo){
        OrderRespVo orderRespVo = orderService.submitOrder(orderSubmitVo);
        if (orderRespVo.getStatus() == 1){
            return R.ok().data("orderResp",orderRespVo);
        }else {
            return R.error();
        }
    }
    /**
     * 获取订单的状态
     */
    @GetMapping("getOrderByOrderSn")
    public R getOrderByOrderSn(@RequestParam(value = "orderSn") String orderSn){
        Order order =  orderService.getOrderByOrderSn(orderSn);
        return R.ok().data("order",JSON.toJSONString(order));
    }

    /**
     * 根据订单号来查询订单信息
     */
    @GetMapping("getOrderInfoById")
    public R getOrderInfoById(@RequestParam(value = "orderIn") String orderId){
        OrderRespVo orderRespVo = orderService.getOrderInfoById(orderId);
        return R.ok().data("orderResp",orderRespVo);
    }

    /**
     * 获取用户的订单列表
     * @param request
     * @return
     */
    @GetMapping("getMemberOrders")
    public R getMemberOrders(HttpServletRequest request){

        List<OrderInfoVo> orderInfoList = orderService.getMemberOrders(request);
        return R.ok().data("orderInfoList",orderInfoList);
    }

    /**
     * 处理订单支付
     */
    @PostMapping("paySuccess")
    public R paySuccess(@RequestBody Order order){
        String orderSn = orderService.paySuccess(order);
        if (orderSn != null){
            return R.ok().data("orderSn",orderSn);
        }
        return R.error();
    }

    /**
     * 删除订单(逻辑删除)
     */
    @PostMapping("deleteOrder")
    public R deleteOrder(@RequestParam String orderId){
        orderService.deleteOrder(orderId);
        return R.ok();
    }

    /**
     * 测试rabbitmq发消息
     * @return
     */
    @GetMapping("test/createOrder")
    public String createOrder(){
        Order order = new Order();
        order.setOrderSn(UUID.randomUUID().toString());
        order.setModifyTime(new Date());
        rabbitTemplate.convertAndSend("order-event-exchange","order.create.order",order);
        return "ok";
    }

}
