package hust.wang.hhyx.feign;

import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author wangmh
 * @Date 2021/1/30 3:04 下午
 **/
@FeignClient("hhyx-seckill")
public interface SeckillFeignService {
    @GetMapping("api/seckill/sgu/seckill/{sguId}")
    public R getSguSeckillInfo(@PathVariable("sguId") String sguId);
}
