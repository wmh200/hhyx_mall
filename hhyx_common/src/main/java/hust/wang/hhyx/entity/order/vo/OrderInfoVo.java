package hust.wang.hhyx.entity.order.vo;

import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.entity.order.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/28 8:46 下午
 **/
@Data
public class OrderInfoVo {
    private Order order;
    private List<OrderItem> orderItemList;
}
