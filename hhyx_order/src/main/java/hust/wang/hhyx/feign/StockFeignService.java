package hust.wang.hhyx.feign;

import hust.wang.hhyx.entity.stock.vo.StockSkuLockVo;
import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author wangmh
 * @Date 2021/1/25 2:06 下午
 **/
@FeignClient("hhyx-stock")
public interface StockFeignService {
    @PostMapping("api/stock/order/lock")
    public R orderLockStock(@RequestBody StockSkuLockVo stockSkuLockVo);
}
