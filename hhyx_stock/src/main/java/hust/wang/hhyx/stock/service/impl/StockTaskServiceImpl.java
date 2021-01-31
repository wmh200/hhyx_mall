package hust.wang.hhyx.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hust.wang.hhyx.entity.stock.StockTask;
import hust.wang.hhyx.stock.mapper.StockTaskMapper;
import hust.wang.hhyx.stock.service.StockTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wangmh
 * @Date 2021/1/27 3:17 下午
 **/
@Service
public class StockTaskServiceImpl extends ServiceImpl<StockTaskMapper, StockTask> implements StockTaskService {
   @Autowired
   StockTaskMapper stockTaskMapper;
    /**
     * 根据订单id来查询订单工作表
     * @param orderSn
     * @return
     */
    @Override
    public StockTask selectBySn(String orderSn) {
        QueryWrapper<StockTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_sn",orderSn);
        return stockTaskMapper.selectOne(queryWrapper);
    }
}
