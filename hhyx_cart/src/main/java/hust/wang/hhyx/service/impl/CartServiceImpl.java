package hust.wang.hhyx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import hust.wang.hhyx.entity.member.Member;
import hust.wang.hhyx.feign.MemberService;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.CartService;
import hust.wang.hhyx.util.RedisUtil;
import hust.wang.hhyx.entity.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author wangmh
 * @Date 2021/1/12 8:05 下午
 **/
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private MemberService memberService;

    @Autowired
    ThreadPoolExecutor executor;
    private final String CART_PREFIX = "hhyx:cart:";

    /**
     * 加入到购物车
     * @param cartItems
     * @param request
     */
    @Override
    public void addCart( List<CartItem>  cartItems, HttpServletRequest request) {
        String cartKey = getCartKey(request);
        JSONArray items = JSONArray.parseArray(JSON.toJSONString(cartItems));
        redisUtil.setEx(cartKey,items.toJSONString(),24,TimeUnit.HOURS);
    }

    /**
     * 获取购物车
     * @param request
     * @return
     */
    @Override
    public List<CartItem> getCart(HttpServletRequest request) {
        String cartKey = getCartKey(request);
        String s = redisUtil.get(cartKey);
        List<CartItem> cartList = JSON.parseArray(s, CartItem.class);
        return cartList;
    }

    // 获取购物车key
    private String getCartKey(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken").replace("[", "").replace("]", "");
        String[] split = redisUtil.get(accessToken).split("-");
        String nickname = split[0];
        String phone = split[1];
        Member member = new Member();
        member.setNickName(nickname);
        member.setPhone(phone);
        R r = memberService.getMember(member);
        HashMap map = (HashMap) r.getData().get("member");
        return CART_PREFIX + map.get("id");
    }
}
