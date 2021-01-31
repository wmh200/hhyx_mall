package hust.wang.hhyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hust.wang.hhyx.entity.order.OrderItem;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/25 1:29 下午
 **/

public interface OrderItemService extends IService<OrderItem> {
    void insertItemList(List<OrderItem> orderItems);

    List<OrderItem> selectList(String orderSn);
}
