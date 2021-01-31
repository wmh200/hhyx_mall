package hust.wang.hhyx.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hust.wang.hhyx.entity.es.SkuEsModel;
import hust.wang.hhyx.entity.product.Category;
import hust.wang.hhyx.entity.product.Good;
import hust.wang.hhyx.entity.product.vo.CategoryGoodsVo;
import hust.wang.hhyx.entity.product.vo.GoodRespVo;
import hust.wang.hhyx.entity.product.vo.GoodVo;
import hust.wang.hhyx.entity.seckill.vo.SeckillVo;
import hust.wang.hhyx.entity.stock.Stock;
import hust.wang.hhyx.entity.vo.Top5Products;
import hust.wang.hhyx.feign.SearchFeignService;
import hust.wang.hhyx.feign.SeckillFeignService;
import hust.wang.hhyx.feign.StockFeignService;
import hust.wang.hhyx.mapper.GoodMapper;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.CategoryService;
import hust.wang.hhyx.service.GoodService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author wangmh
 * @Date 2021/1/6 10:37 下午
 **/
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {
    @Autowired
    CategoryService categoryService;

    @Autowired
    GoodMapper goodMapper;

    @Autowired
    SeckillFeignService seckillFeignService;

    @Autowired
    StockFeignService stockFeignService;

    @Autowired
    ThreadPoolExecutor myThread;

    @Autowired
    SearchFeignService searchFeignService;



    @Override
    public List<CategoryGoodsVo> getCategoryAndGoods() {
        List<CategoryGoodsVo> list = new ArrayList<>();
        List<Category> category = categoryService.getCategory();
        category.stream().forEach(cate -> {
            CategoryGoodsVo categoryGoodsVo = new CategoryGoodsVo();
            BeanUtils.copyProperties(cate,categoryGoodsVo);
            String categoryId = cate.getCategoryId();
            QueryWrapper<Good> wrapper = new QueryWrapper<>();
            wrapper.eq("category_id",categoryId);
            List<Good> goods = goodMapper.selectList(wrapper);
            List<GoodVo> goodsVo = new ArrayList<>();
            goods.stream().forEach(good -> {
                GoodVo goodVo = new GoodVo();
                BeanUtils.copyProperties(good,goodVo);
                goodsVo.add(goodVo);
            });
            categoryGoodsVo.setChildren(goodsVo);
            list.add(categoryGoodsVo);
        });
        return list;
    }

    @Override
    public Map<String,Object> getGoodById(String sguId) throws ExecutionException, InterruptedException {
        /**
         * 通过异步调用 ==》 提高效率
         */
        HashMap<String, Object> map = new HashMap<>();
        CompletableFuture<Void> goodFuture = CompletableFuture.runAsync(() ->{
            QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sgu_id",sguId);
            List<Good> goods = goodMapper.selectList(queryWrapper);
            Good goodDetail = goods.get(0);
            String imagesStrs = goodDetail.getImages().toString().trim();
            String str = "";
            for (int j= 1; j < imagesStrs.length()-1; j++) {
                str=str + imagesStrs.charAt(j);
            }
            String[] split = str.split(",");
            map.put("goodDetail",goodDetail);
            map.put("images", Arrays.asList(split));
        },myThread);
        CompletableFuture<Void> stockFuture = CompletableFuture.runAsync(() -> {
            R r = stockFeignService.getSguStock(sguId);
            Stock stock = JSON.parseObject(r.getData().get("stock").toString(), Stock.class);
            map.put("stock",stock);
        },myThread);
        CompletableFuture<Void> seckillFuture = CompletableFuture.runAsync(() ->{
            R sguSeckillInfo = seckillFeignService.getSguSeckillInfo(sguId);
            SeckillVo seckillInfo = null;
            if (sguSeckillInfo.getData().get("seckillInfo") != null) {
                seckillInfo = JSON.parseObject(sguSeckillInfo.getData().get("seckillInfo").toString(), SeckillVo.class);
            }
            map.put("seckillInfo",seckillInfo);
        },myThread);

        CompletableFuture.allOf(goodFuture,stockFuture,seckillFuture).get();
        return map;
    }

    @Override
    public List<Good> getNewUserProducts() {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_status",1);
        List<Good> goods = goodMapper.selectList(queryWrapper);
        return goods;
    }

    @Override
    public List<Good> getIndexRecommend() {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_status",2);
        List<Good> goods = goodMapper.selectList(queryWrapper);
        return goods;
    }

    @Override
    public List<Top5Products> getTopProducts() {
        List<Category> categorys = categoryService.getCategory();
        List<Top5Products> results = new ArrayList<>();
        categorys.forEach(category -> {
            Top5Products top5Products = new Top5Products();
            top5Products.setCategory_id(category.getCategoryId());
            top5Products.setCategory_name(category.getCategoryName());
            top5Products.setCategory_image(category.getIconUrl());
            QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("category_id",category.getCategoryId());
            /**
             * TODO  后面要根据销量来的
             */
            queryWrapper.last("limit 6");
            List<Good> goods = goodMapper.selectList(queryWrapper);
            top5Products.setGoodList(goods);
            results.add(top5Products);
        });
        return results;
    }

    @Override
    public List<Good> getGoodList() {
        List<Good> goods = goodMapper.selectList(null);
        return goods;
    }

    @Override
    public Good getGoodBySguId(String sguId) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sgu_id",sguId);
        Good good = goodMapper.selectOne(queryWrapper);
        return good;
    }

    @Override
    public void uploadGood() {
        List<Good> goods = goodMapper.selectList(null);
        List<SkuEsModel> list = new ArrayList<>();
        for (Good good : goods) {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(good,skuEsModel);
            skuEsModel.setSellNum(good.getTotalSales());
            skuEsModel.setStock(good.getSellableStock());
            list.add(skuEsModel);
        }
        R r = searchFeignService.productsUpload(list);
        System.out.println(r);
    }

}
