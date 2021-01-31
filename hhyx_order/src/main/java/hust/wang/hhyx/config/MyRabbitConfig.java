package hust.wang.hhyx.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangmh
 * @Date 2021/1/20 10:38 上午
 **/

/**
 * spring:
 *   rabbitmq:
 *     addresses: 120.26.178.67
 *     port: 5672
 *     virtual-host: /
 *     # 开启发送端确认
 *     publisher-confirms: true
 *     # 开启发送端消息抵达队列的确认
 *     publisher-returns: true
 *     # 只要抵达队列，以异步发送优先回调我们这个returnconfirm
 *     template:
 *        mandatory: true
 */
@Configuration
public class MyRabbitConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * p292
     * @Bean Binding,Queue,Exchange
     * 容器中 Binding,Queue,Exchange 会自动创建
     */

    /**
     * (String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
     * @return
     */
    @Bean
    public Queue orderDelayQueue(){
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange","order-event-exchange");
        arguments.put("x-dead-letter-routing-key","order.release.order");
        arguments.put("x-message-ttl",60*1000*15);
        Queue queue = new Queue("order.delay.queue", true, false, false,arguments);
        return queue;
    }

    @Bean
    public Queue orderReleaseQueue(){
        Queue queue = new Queue("order.release.order.queue", true, false, false);
        return queue;
    }

    /**
     * String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
     * @return
     */
    @Bean
    public Exchange orderExchange(){
        return new TopicExchange("order-event-exchange",true,false);
    }

    /**
     * String destination, Binding.DestinationType destinationType, String exchange, String routingKey, Map<String, Object> arguments
     * @return
     */
    @Bean
    public Binding orderCreateOrderBinding(){
        return new Binding("order.delay.queue"
                , Binding.DestinationType.QUEUE
                ,"order-event-exchange"
                ,"order.create.order"
                ,null);
    }
    @Bean
    public Binding orderReleaseOrderBinding(){
        return new Binding("order.release.order.queue"
                , Binding.DestinationType.QUEUE
                ,"order-event-exchange"
                ,"order.release.order"
                ,null);
    }
    @Bean
    public Binding orderReleaseOtherBinding(){
        return new Binding("stock.release.stock.queue"
                , Binding.DestinationType.QUEUE
                ,"order-event-exchange"
                ,"order.release.other.#"
                ,null);
    }
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     *
     * 定制化 RabbitTemplate
     *     @PostConstruct MyRabbitConfig对象创建完构造器以后调用这个方法
     *
     *
     *
     * 1. RabbitTemplate  服务收到消息就回调
     *     CorrelationData 当前消息关联的唯一数据 (这个是消息的唯一id)
     *     ack 消息是否成功收到
     *     cause 消息没有接收到原因
     *
     * 2. 消息正确抵达队列进行回调
     *     new RabbitTemplate.ReturnCallback()
     *
     * 3.消费端确认（保证每个消息被正确消费，此时才可以broker删除这个消息
     *      1.默认是自动确认的，只要消息接收到，客户端会自动确认，服务端就会移除这个消息
     *         问题：
     *            我们收到很多消息，自动回复给服务器ack，只有一个消息处理成功，宕机了。发生消息丢失；
     *            手动确认模式：只要我们没有明确告诉MQ，货物被签收。没有Ack，消息就一直是unacked状态，即使
     *                       Comsumer宕机。消息不会丢失，会重新变为Ready，下一次有新的Consumer连接进来
     *                       就发给它
     *      2.如何签收：
     *
     *
     * @return
     */
    @PostConstruct
    public void initRabbitTemplate(){
        //设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            // 服务器收到了
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm。。。correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);
            }
        });

        //设置消息抵达队列的确认回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败回调
             * @param message   哪个投递失败的消息详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange  当时这个消息发给哪个交换机
             * @param exchange  当时这个消息用哪个路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routeKey) {
                //报错误了。修改数据库当前消息的错误状态->错误
                System.out.println("message===>"+message+",replyCode===>"+replyCode+",replyText===>"+replyText+",exchange===>"+exchange+",routeKey===>"+routeKey);
            }
        });
    }

    /**
     * 秒杀模块消峰
     * @return
     */
    @Bean
    public Queue orderSeckillOrderQueue(){
        Queue queue = new Queue("order.seckill.order.queue", true, false, false);
        return queue;
    }

    @Bean
    public Binding orderSeckillOrderBinding(){
        return new Binding("order.seckill.order.queue"
                , Binding.DestinationType.QUEUE
                ,"order-event-exchange"
                ,"order.seckill.order"
                ,null);
    }
}
