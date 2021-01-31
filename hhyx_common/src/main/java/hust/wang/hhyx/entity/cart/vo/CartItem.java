package hust.wang.hhyx.entity.cart.vo;


import lombok.Data;

/**
 * 购物项
 * @Author wangmh
 * @Date 2021/1/12 7:28 下午
 **/
@Data
public class CartItem {
    /**
     * 商品id
     */
    private String sguId;

    /**
     * 是否被选中
     */
    private Boolean check = true;

    /**
     * 商品名称
     */
    public String sguName;

    /**
     * 商品图片
     */
    public String bigImageUrl;

    /**
     * 商品售价
     */
    public Double salePrice;

    /**
     * 商品数量
     */
    public Integer count;

}
