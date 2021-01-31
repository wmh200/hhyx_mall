package hust.wang.hhyx.entity.order.vo;

import hust.wang.hhyx.entity.cart.vo.CartItem;
import hust.wang.hhyx.entity.member.vo.MemberPositionVo;
import hust.wang.hhyx.entity.member.vo.MemberVo;
import hust.wang.hhyx.entity.shopkeeper.ShopKeeper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/24 12:53 下午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitVo {
    private List<CartItem> cartList;
    private MemberPositionVo position;
    private String note;
    private Double payAmount;
    private Double reduceAmount;
    private Double totalAmount;
    private ShopKeeper shopkeeper;
    private MemberVo userInfo;
    private String orderToken;
}
