package hust.wang.hhyx.feign;

import hust.wang.hhyx.entity.es.SkuEsModel;
import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/31 3:52 下午
 **/
@FeignClient("hhyx-search")
public interface SearchFeignService {
    @PostMapping("api/search/save/product")
    public R productsUpload(@RequestBody List<SkuEsModel> skuEsModels);
}
