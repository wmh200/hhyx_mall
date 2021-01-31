package hust.wang.hhyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hust.wang.hhyx.entity.order.OrderItem;
import hust.wang.hhyx.mapper.OrderItemMapper;
import hust.wang.hhyx.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/25 1:29 下午
 **/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
   @Autowired
   OrderItemMapper orderItemMapper;

    /**
     * 保存所有的订单项
     * @param orderItems
     */
    @Override
    public void insertItemList(List<OrderItem> orderItems) {
        orderItems.stream().forEach(orderItem -> {
            orderItemMapper.insert(orderItem);
        });
    }

    /**
     * 根据订单号查询所有订单项
     * @param orderSn
     * @return
     */
    @Override
    public List<OrderItem> selectList(String orderSn) {
        return orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_sn",orderSn));
    }
}
