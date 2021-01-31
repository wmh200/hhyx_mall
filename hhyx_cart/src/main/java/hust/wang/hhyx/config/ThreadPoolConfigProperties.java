package hust.wang.hhyx.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author wangmh
 * @Date 2021/1/18 9:53 下午
 **/
@Data
@Component
public class ThreadPoolConfigProperties {
    private Integer coreSize = 20;
    private Integer maxSize = 200;
    private Integer keepAliveTime = 10;
}
