package hust.wang.hhyx.feign;

import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author wangmh
 * @Date 2021/1/30 3:58 下午
 **/
@FeignClient("hhyx-stock")
public interface StockFeignService {
    @GetMapping("api/stock/getSguStock")
    public R getSguStock(@RequestParam(value = "sugId") String sguId);
}
