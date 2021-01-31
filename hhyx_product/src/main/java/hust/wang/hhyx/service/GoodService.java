package hust.wang.hhyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hust.wang.hhyx.entity.product.Category;
import hust.wang.hhyx.entity.product.Good;
import hust.wang.hhyx.entity.product.vo.CategoryGoodsVo;
import hust.wang.hhyx.entity.product.vo.GoodRespVo;
import hust.wang.hhyx.entity.stock.vo.StockVo;
import hust.wang.hhyx.entity.vo.Top5Products;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Author wangmh
 * @Date 2021/1/6 10:36 下午
 **/
public interface GoodService extends IService<Good> {
    /**
     * 获取品类和商品信息
     * @return
     */
    List<CategoryGoodsVo> getCategoryAndGoods();

    /**
     * 根据id来获取商品
     * @param prodId
     * @return
     */
    Map<String, Object> getGoodById(String prodId) throws ExecutionException, InterruptedException;

    /**
     * 获取新用户专享商品
     * @return
     */
    List<Good> getNewUserProducts();

    /**
     * 获取首页推荐商品
     * @return
     */
    List<Good> getIndexRecommend();

    /**
     * 获取首页每个品类销量前五的商品
     * @return
     */
    List<Top5Products> getTopProducts();

    /**
     * 获取库存的一些信息，初始化库存
     * @return
     */
    List<Good> getGoodList();

    /**
     * 根据sguId来查询商品
     * @param sguId
     * @return
     */
    Good getGoodBySguId(String sguId);

    void uploadGood();
}
