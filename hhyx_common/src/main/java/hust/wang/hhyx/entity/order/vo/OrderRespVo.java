package hust.wang.hhyx.entity.order.vo;


import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.entity.order.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/25 7:27 下午
 **/
@Data
public class OrderRespVo {
    /**
     * 商品状态：1.正常 2 穿过来的数据与计算的总价格不一致 3.库存不足 4.查询订单信息
     */
    private int status;
    private Order orderEntity;
    private List<OrderItem> itemList;
}
