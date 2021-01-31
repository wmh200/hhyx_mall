package hust.wang.hhyx.controller;

import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.CartService;
import hust.wang.hhyx.entity.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/12 8:05 下午
 **/
@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 增加购物车数据到缓存中
     * @param cartItems
     * @param request
     * @return
     */
    @PostMapping("addCart")
    public R addCart(@RequestBody List<CartItem>  cartItems, HttpServletRequest request){
        cartService.addCart(cartItems,request);
        return R.ok().message("购物车添加成功");
    }

    /**
     * 登录时候获取缓存中的购物车数据
     * @param request
     * @return
     */
    @GetMapping("getCart")
    public R getCart(HttpServletRequest request){
        List<CartItem> items = cartService.getCart(request);
        return R.ok().data("cartList",items);
    }

}
