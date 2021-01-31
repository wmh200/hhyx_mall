package hust.wang.hhyx.controller;

import hust.wang.hhyx.entity.es.SkuEsModel;
import hust.wang.hhyx.result.CodeMsg;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/31 3:19 下午
 **/
@RestController
@RequestMapping("search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @PostMapping("save/product")
    public R productsUpload(@RequestBody List<SkuEsModel> skuEsModels){
        boolean b = false;
        try {
            b = searchService.save(skuEsModels);
        } catch (IOException e) {
            return R.setResult(CodeMsg.PRODUCT_UP_EXCEPTION);
        }
        if(b){
            return R.ok();
        }else {
            return R.setResult(CodeMsg.PRODUCT_UP_EXCEPTION);
        }

    }

    @GetMapping("getProductBykeyword")
    public R getProductsByKeyword(@RequestParam("keyword") String keyword){
        List<SkuEsModel>skuEsModels = searchService.getProductsByKeyword(keyword);
        return R.ok().data("products",skuEsModels);
    }
}
