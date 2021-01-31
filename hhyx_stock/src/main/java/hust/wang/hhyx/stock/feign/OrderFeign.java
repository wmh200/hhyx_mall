package hust.wang.hhyx.stock.feign;

import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author wangmh
 * @Date 2021/1/27 11:00 下午
 **/
@FeignClient("hhyx-order")
public interface OrderFeign {
    @GetMapping("api/order/getOrderByOrderSn")
    public R getOrderByOrderSn(@RequestParam(value = "orderSn") String orderSn);
}
