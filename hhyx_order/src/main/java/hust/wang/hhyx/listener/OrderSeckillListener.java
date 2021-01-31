package hust.wang.hhyx.listener;

import com.rabbitmq.client.Channel;
import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.entity.seckill.vo.SeckillOrderVo;
import hust.wang.hhyx.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author wangmh
 * @Date 2021/1/30 10:07 下午
 **/
@Slf4j
@Component
@RabbitListener(queues = "order.seckill.order")
public class OrderSeckillListener {
    @Autowired
    OrderService orderService;
    @RabbitHandler
    public void listener(SeckillOrderVo seckillOrderVo, Channel channel, Message message) throws IOException {
        try {
            log.info("准备创建秒杀单的详细信息。。。");
            orderService.createSeckillOrder(seckillOrderVo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
}
