package hust.wang.hhyx.service.impl;
import com.rabbitmq.client.Channel;
import hust.wang.hhyx.entity.member.Member;
import hust.wang.hhyx.entity.product.Category;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

/**
 * @Author wangmh
 * @Date 2021/1/24 2:15 下午
 **/
@RabbitListener(queues = {"hello-java-queue"})
public class RabbitService {
    /**
     * queues是监听的所有队列
     * Channel channel 当前传输数据的通道
     *
     *
     * Queue: 可以很多人都来监听。只要收到消息 队列删除消息 而且只能有一个收到此消息
     *
     *    1.订单服务启动多个 同一个消息只能有一个客户端接收到
     *    2.只有一个消息完全接收完 才能接收下一个消息
     *
     *    @RabbitListener 可以标在类和方法上 @RabbitHandle只能标在方法上
     */
    @RabbitHandler
    public void recieveMessage(Message message, Member member, Channel channel){
        System.out.println("接收到消息："+member+",类型为："+member.getClass());
        //channel内按顺序自增的
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println(deliveryTag);
        //签收货物，非批量模式
        try {
            if(deliveryTag % 2 == 0){
                //收货
                channel.basicAck(deliveryTag,false);
                System.out.println("签收了货物。。"+deliveryTag);
            }else{
                // long deliveryTag, boolean multiple, boolean requeue 是否丢弃 true 重新发回服务器
                channel.basicNack(deliveryTag,false,false);
                // long deliveryTag, boolean requeue,
//                channel.basicReject();
                System.out.println("没有签收了货物。。。"+deliveryTag);
            }

        } catch (IOException e) {
            //网路中断
            e.printStackTrace();
        }
    }

    @RabbitHandler
    public void recieveMessage2(Message message, Category category, Channel channel){
        System.out.println("接收到消息："+category+",类型为："+category.getClass());
    }
}
