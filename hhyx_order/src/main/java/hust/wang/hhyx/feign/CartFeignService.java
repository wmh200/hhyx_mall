package hust.wang.hhyx.feign;

import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wangmh
 * @Date 2021/1/25 12:00 上午
 **/
@FeignClient(value = "hhyx-cart")
public interface CartFeignService {
    @GetMapping("api/cart/getCart")
    public R getCart();
}
