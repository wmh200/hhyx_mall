package hust.wang.hhyx.feign;

import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author wangmh
 * @Date 2021/1/29 11:31 下午
 **/
@FeignClient("hhyx-product")
public interface ProductFeignService {
    @GetMapping("api/product/getGoodBySguId")
    public R getGoodBySguId(@RequestParam(value = "sguId") String sguId);
}
