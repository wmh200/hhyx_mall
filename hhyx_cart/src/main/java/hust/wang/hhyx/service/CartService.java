package hust.wang.hhyx.service;

import hust.wang.hhyx.entity.cart.vo.CartItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/12 8:02 下午
 **/
public interface CartService {
    void addCart(List<CartItem>  cartItems, HttpServletRequest request);

    List<CartItem> getCart(HttpServletRequest request);
}
