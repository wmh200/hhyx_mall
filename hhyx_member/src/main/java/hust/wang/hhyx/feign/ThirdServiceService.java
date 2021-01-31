package hust.wang.hhyx.feign;

import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author wangmh
 * @Date 2021/1/16 4:10 下午
 **/
@FeignClient("hhyx-third-service")
public interface ThirdServiceService {
    @GetMapping("third/msm/sendCode")
    public R sendCode(@RequestParam(value = "phone") String phone, @RequestParam(value = "code") String code);
}
