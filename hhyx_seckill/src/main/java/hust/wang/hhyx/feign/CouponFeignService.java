package hust.wang.hhyx.feign;

import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author wangmh
 * @Date 2021/1/29 9:43 下午
 **/
@FeignClient("hhyx-coupon")
public interface CouponFeignService {
    @GetMapping("api/coupon/seckill/latest3DaySession")
    public R getLatest3DaySession();
}
