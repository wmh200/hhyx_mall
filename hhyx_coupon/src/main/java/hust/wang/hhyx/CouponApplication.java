package hust.wang.hhyx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author wangmh
 * @Date 2021/1/29 7:07 下午
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("hust.wang.hhyx.mapper")
public class CouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class,args);
    }
}
