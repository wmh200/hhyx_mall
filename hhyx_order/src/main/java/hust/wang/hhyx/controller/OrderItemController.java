package hust.wang.hhyx.controller;

import hust.wang.hhyx.entity.order.OrderItem;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/25 1:26 下午
 **/
@RestController
@RequestMapping("order/item")
public class OrderItemController {
    @Autowired
    OrderItemService orderItemService;

    @PostMapping("insertItemList")
    public R insertItemList(@RequestBody List<OrderItem> orderItems){
        orderItemService.insertItemList(orderItems);
        return R.ok();
    }
}
