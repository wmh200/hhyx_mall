package hust.wang.hhyx.entity.stock.vo;

import hust.wang.hhyx.entity.order.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/25 1:49 下午
 **/
@Data
public class StockSkuLockVo {
    private String orderSn;
    private List<OrderItem> orderItems;
}
