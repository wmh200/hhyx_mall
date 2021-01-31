package hust.wang.hhyx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author wangmh
 * @Date 2020/12/30 8:58 上午
 **/

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
@MapperScan("hust.wang.hhyx.mapper")
@EnableFeignClients
@EnableAspectJAutoProxy(exposeProxy = true)
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
