package hust.wang.hhyx.entity.order.vo;

import hust.wang.hhyx.entity.member.MemberDelivery;
import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.entity.order.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/24 3:20 下午
 **/
@Data
public class OrderCreateVo {
    /**
     * 订单实体
     */
    private Order order;
    /**
     * 订单计算的应付价格
     */
    private List<OrderItem> orderItems;
    /**
     * 订单应付价格
     */
    private Double payPrice;

    private MemberDelivery memberDelivery;
}
