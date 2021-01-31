package hust.wang.hhyx.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wangmh
 * @Date 2021/1/30 12:02 上午
 **/
@Configuration
public class MyRedissonConfig {
    /**
     * 所有对Redisson的使用都是通过RedissonClient对象
     * @return
     */
    @Bean
    public RedissonClient redisson()  {
        //1.创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://120.26.178.67:6379");
        //2.根据Config创建出RedissonClient示例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
