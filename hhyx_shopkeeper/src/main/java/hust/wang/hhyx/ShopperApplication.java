package hust.wang.hhyx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author wangmh
 * @Date 2021/1/20 11:20 上午
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("hust.wang.hhyx.mapper")
public class ShopperApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopperApplication.class);
    }
}
