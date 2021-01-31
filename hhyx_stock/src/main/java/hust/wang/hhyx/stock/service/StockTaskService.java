package hust.wang.hhyx.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hust.wang.hhyx.entity.stock.StockDetail;
import hust.wang.hhyx.entity.stock.StockTask;

/**
 * @Author wangmh
 * @Date 2021/1/27 3:16 下午
 **/

public interface StockTaskService extends IService<StockTask> {
    StockTask selectBySn(String orderSn);
}
