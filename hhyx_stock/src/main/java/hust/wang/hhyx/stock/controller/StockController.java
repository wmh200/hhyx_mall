package hust.wang.hhyx.stock.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import hust.wang.hhyx.entity.stock.Stock;
import hust.wang.hhyx.entity.stock.vo.LockStockResult;
import hust.wang.hhyx.entity.stock.vo.StockSkuLockVo;
import hust.wang.hhyx.exception.NoStockException;
import hust.wang.hhyx.result.CodeMsg;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author wangmh
 * @Date 2021/1/24 8:47 上午
 **/
@RestController
@RequestMapping("stock")
public class StockController {
    @Autowired
    StockService stockService;

    @PostMapping("insertStocks")
    public R insertStocks(){
        stockService.insertStocks();
        return R.ok();
    }

    @PostMapping("/order/lock")
    public R orderLockStock(@RequestBody StockSkuLockVo stockSkuLockVo){
        try{
            stockService.orderLockStock(stockSkuLockVo);
            return R.ok();
        }catch (NoStockException e){
            return R.setResult(CodeMsg.NO_STOCK_EXCEPTION);
        }
    }

    @GetMapping("getSguStock")
    public R getSguStock(@RequestParam(value = "sugId") String sguId){
        Stock stock = stockService.getSguStock(sguId);
        return R.ok().data("stock",JSON.toJSONString(stock));
    }
}
