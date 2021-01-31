package hust.wang.hhyx.listener;

import com.rabbitmq.client.Channel;
import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author wangmh
 * @Date 2021/1/28 10:23 上午
 **/
@Service
@RabbitListener(queues = "order.release.order.queue")
public class OrderCloseListener {
    @Autowired
    OrderService orderService;

    @RabbitHandler
    public void listener(Order order, Channel channel, Message message) throws IOException {
        System.out.println("收到过期的订单信息:准备关闭订单"+order.getOrderSn());
        try {
            orderService.closeOrder(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }

}
