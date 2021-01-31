package hust.wang.hhyx.entity.seckill.vo;

import lombok.Data;

/**
 * @Author wangmh
 * @Date 2021/1/30 9:13 下午
 **/
@Data
public class SeckillOrderVo {
    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 秒杀场次id
     */
    private Integer promotionSessionId;
    /**
     * 秒杀的商品id
     */
    private String sguId;
    /**
     * 秒杀的商品价格
     */
    private Double seckillPrice;
    /**
     * 购买的商品数量
     */
    private Integer num;

    /**
     * 用户的openId
     */
    private String openId;

}
