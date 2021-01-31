package hust.wang.hhyx.stock.feign;

import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author wangmh
 * @Date 2021/1/24 9:08 上午
 **/
@FeignClient("hhyx-product")
public interface ProductFeign {
    @GetMapping("api/product/getGoodList")
    public R getStockInfo();
}
