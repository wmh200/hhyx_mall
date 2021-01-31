package hust.wang.hhyx.controller;

import com.alibaba.fastjson.JSON;
import hust.wang.hhyx.entity.product.Category;
import hust.wang.hhyx.entity.product.Good;
import hust.wang.hhyx.entity.product.vo.CategoryGoodsVo;
import hust.wang.hhyx.entity.stock.vo.StockVo;
import hust.wang.hhyx.entity.vo.Top5Products;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.CategoryService;
import hust.wang.hhyx.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Author wangmh
 * @Date 2021/1/6 10:20 下午
 **/
@Api(value = "product管理")  //对controller进行描述
@RestController
@RequestMapping(value = "product")
public class ProductController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodService goodService;

    @ApiOperation(value = "获取所有品类和商品接口") //对接口进行描述
    @GetMapping("/categoryAndGoods")
    public R getCategoryAndGoods(){
        List<CategoryGoodsVo> list = goodService.getCategoryAndGoods();
        return R.ok().data("itemList",list);
    }

    @ApiOperation(value = "获取所有品类接口") //对接口进行描述
    @GetMapping("/category")
    public R getCategory(){
        List<Category> list = categoryService.getCategory();
        return R.ok().data("category", list);
    }


    @ApiOperation(value = "获取所有品类接口") //对接口进行描述
    @GetMapping("/goodById")
    public R getGoodById(@RequestParam(value = "sguId") String sguId) throws ExecutionException, InterruptedException {
        Map<String,Object> result = goodService.getGoodById(sguId);
        return R.ok().data(result);
    }

    /**
     * 获取新用户专享商品
     */
    @GetMapping("/newUserProduct")
    public R getNewUserProducts(){
        List<Good> list = goodService.getNewUserProducts();
        return R.ok().data("newProduct",list);
    }

    /**
     * 获取首页推荐
     */
    @GetMapping("/getIndexRecommend")
    public R getIndexRecommend(){
        List<Good> recommendLists  = goodService.getIndexRecommend();
        return R.ok().data("RecommendList",recommendLists);
    }

    /**
     * 获取每个品类销量最高的的几个商品
     */
    @GetMapping("/getTopProducts")
    public R getTopProducts(){
        List<Top5Products> list = goodService.getTopProducts();
        return R.ok().data("top5List",list);
    }

    /**
     * 获取所有的商品
     * @return
     */
    @GetMapping("/getGoodList")
    public R getStockInfo(){
        List<Good> list = goodService.getGoodList();
        String s = JSON.toJSONString(list);
        return R.ok().data("stockInfo",s);
    }

    /**
     * 通过sguId来获取商品
     */
    @GetMapping("getGoodBySguId")
    public R getGoodBySguId(@RequestParam(value = "sguId") String sguId){
        Good good = goodService.getGoodBySguId(sguId);
        return R.ok().data("good", JSON.toJSONString(good));
    }

    /**
     * 上架商品到es里面
     * @return
     */
    @PostMapping("uploadGood")
    public R uploadGood(){
        goodService.uploadGood();
        return R.ok();
    }
}
