package hust.wang.hhyx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author wangmh
 * @Date 2021/1/29 7:22 下午
 **/
//@EnableRabbit  监听消息就加 不监听就不加
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class,args);
    }
}
