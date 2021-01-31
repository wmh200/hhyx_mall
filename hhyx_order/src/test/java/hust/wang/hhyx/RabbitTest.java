//package hust.wang.hhyx;
//
//import hust.wang.hhyx.entity.member.Member;
//import hust.wang.hhyx.entity.product.Category;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.UUID;
//
///**
// * @Author wangmh
// * @Date 2021/1/19 11:45 下午
// **/
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Slf4j
//public class RabbitTest {
//    @Autowired
//    AmqpAdmin amqpAdmin;
//
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//    /**
//     * 1.如何创建Exchange Queue Binding
//     *   1)使用AmqpAdmin进行创建
//     *
//     *      属性绑定
//     *     @ConfigurationProperties(
//     *     prefix = "spring.rabbitmq"
//     * )
//     */
//    @Test
//    public void sendMessage(){
//        for (int i = 0; i < 10; i++){
//            if(i % 2 == 0){
//                Member member = new Member();
//                member.setPhone("111111111");
//                member.setNickName("haha");
//                rabbitTemplate.convertAndSend("hello-java-exchange","hello-java",member);
//            }else{
//                Category category = new Category();
//                category.setCategoryId(UUID.randomUUID().toString());
//                rabbitTemplate.convertAndSend("hello-java-exchange","hello-java",category);
//            }
//        }
//
//        log.info("发送消息");
//    }
//
//    @Test
//    public void createExchange(){
//        /**
//         * public DirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
//         */
//        DirectExchange directExchange = new DirectExchange("hello-java-exchange",true,false);
//        amqpAdmin.declareExchange(directExchange);
//        log.info("交换机创建完成");
//    }
//
//    @Test
//    public void createQueue(){
//        /**
//         *  public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete)
//         */
//        Queue queue = new Queue("hello-java-queue",true,false,false);
//        amqpAdmin.declareQueue(queue);
//        log.info("队列创建成功"+queue);
//    }
//
//    @Test
//    public void createBinding(){
//        /**
//         * (String destination[目的地],
//         * Binding.DestinationType destinationType【目的地类型】,
//         *   String exchange,【交换机】
//         *   String routingKey,【路由键】
//         *   Map<String, Object> arguments)【自定义参数】 {
//         */
//        Binding binding = new Binding("hello-java-queue",Binding.DestinationType.QUEUE,"hello-java-exchange","hello-java",null);
//        amqpAdmin.declareBinding(binding);
//        log.info("绑定创建成功"+binding);
//    }
//
//
//
//
//
//}
