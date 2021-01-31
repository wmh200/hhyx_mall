package hust.wang.hhyx.stock.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangmh
 * @Date 2021/1/27 2:14 下午
 **/
@Configuration
public class MyRabbitConfig {

    /**
     * 交换机  String name, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
     *         super(name, durable, autoDelete, arguments
     * @return
     */
    @Bean
    public Exchange stockEventExchange(){
        return new TopicExchange("stock-event-exchange",true,false);
    }

    /**
     * 队列 ：String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
     * @return
     */
    @Bean
    public Queue stockReleaseStockQueue(){
        return new Queue("stock.release.stock.queue",true,false,false);
    }

    /**
     * 延迟队列
     * @return
     */
    @Bean
    public Queue stockDelayQueue(){
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange","stock-event-exchange");
        arguments.put("x-dead-letter-routing-key","stock.release.stock");
        arguments.put("x-message-ttl",60*1000*16);
        return new Queue("stock.delay.queue",true,false,false,arguments);
    }

    /**
     * 延时队列绑定关系   String destination, Binding.DestinationType destinationType,
     *          String exchange,String routingKey, Map<String, Object> arguments
     * @return
     */
    @Bean
    public Binding stockLockedBinding(){
        return new Binding("stock.delay.queue"
                   , Binding.DestinationType.QUEUE
                   ,"stock-event-exchange"
                   ,"stock.locked"
                   ,null);
    }
    /**
     * 普通队列绑定关系   String destination, Binding.DestinationType destinationType,
     *          String exchange,String routingKey, Map<String, Object> arguments
     * @return
     */
    @Bean
    public Binding stockReleaseBinding(){
        return new Binding("stock.release.stock.queue"
                , Binding.DestinationType.QUEUE
                ,"stock-event-exchange"
                ,"stock.release.stock"
                ,null);
    }
    /**
     * json形式接收数据
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
