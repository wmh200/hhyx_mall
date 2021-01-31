package hust.wang.hhyx.schedule;

import hust.wang.hhyx.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author wangmh
 * @Date 2021/1/29 8:12 下午
 **/
@Slf4j
@Service
/**
 * 每天凌晨3点 上架最近三天需要秒杀的商品
 */
public class SeckillSguSchedule {
    @Autowired
    SeckillService seckillService;

    @Autowired
    RedissonClient redissonClient;

    private final String upload_lock = "seckill:upload:lock";

    //TODO 幂等性处理 上架过了就不用上架了
    @Scheduled(cron = "*/10 * * * * ?")
    public void uploadSeckillSguLatest3Days(){
        // 1.重复上架无需处理
        log.info("上架的秒杀的商品信息");
        //分布式锁。锁的业务执行完成，状态已经更新完成。释放锁以后。其他人获取到就会拿到最新的状态
        RLock lock = redissonClient.getLock(upload_lock);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            seckillService.uploadSeckillSguLatest3Days();
        }finally {
            lock.unlock();
        }

    }
}
