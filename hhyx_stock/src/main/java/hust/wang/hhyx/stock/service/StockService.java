package hust.wang.hhyx.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hust.wang.hhyx.entity.mq.stock.StockLockedTo;
import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.entity.stock.Stock;
import hust.wang.hhyx.entity.stock.vo.LockStockResult;
import hust.wang.hhyx.entity.stock.vo.StockSkuLockVo;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/24 8:55 上午
 **/
public interface StockService extends IService<Stock> {
    void insertStocks();

    Boolean orderLockStock(StockSkuLockVo stockSkuLockVo);


    void updateStock(String skuId, int num);

    void unlockStock(StockLockedTo stockLockedTo);

    void unlockStock(Order order);

    Stock getSguStock(String sguId);
}
