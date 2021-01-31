package hust.wang.hhyx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author wangmh
 * @Date 2020/12/29 4:00 下午
 **/
@SpringBootApplication
//@EnableSwagger2
@MapperScan("hust.wang.hhyx.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class GoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodApplication.class,args);
    }
}
