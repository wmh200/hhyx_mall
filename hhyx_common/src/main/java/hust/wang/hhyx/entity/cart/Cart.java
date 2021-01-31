package hust.wang.hhyx.entity.cart;

/**
 * @Author wangmh
 * @Date 2021/1/12 7:26 下午
 **/

import hust.wang.hhyx.entity.cart.vo.CartItem;

import java.util.List;

/**
 * 购物车
 */
public class Cart {
    /**
     * 商品项
     */
    private List<CartItem> items;

    /**
     * 商品数量
     */
    private Integer count;

    private Double totalAmount;

    private Double reduce;

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCount() {
        int countNum = 0;
        if(items != null && items.size()>0){
            for (CartItem item : items) {
                countNum += item.getCount();

            }
        }
        return countNum;
    }

    public Double getTotalAmount() {
        double amount = 0;
        if(items != null && items.size()>0){
            for (CartItem item: items){
                amount += item.getSalePrice() * item.getCount();
            }
        }
        amount -= getReduce();
        return amount;
    }

    public Double getReduce() {
        return reduce;
    }

    public void setReduce(Double reduce) {
        this.reduce = reduce;
    }
}
