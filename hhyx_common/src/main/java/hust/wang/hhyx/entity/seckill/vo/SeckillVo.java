package hust.wang.hhyx.entity.seckill.vo;
import lombok.Data;

/**
 * @Author wangmh
 * @Date 2021/1/30 3:11 下午
 **/
@Data
public class SeckillVo {
    /**
     * 秒杀详情表id
     */
    private Integer id;
    /**
     * 秒杀活动的id
     */
    private Integer promotionId;
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
     * 秒杀的商品数量
     */
    private Integer seckillCount;
    /**
     * 秒杀的商品的限制购买数
     */
    private Integer seckillLimit;
    /**
     * 秒杀商品的排序
     */
    private Integer seckillSort;
    /**
     * 秒杀开始时间
     */
    private Long startTime;
    /**
     * 秒杀结束时间
     */
    private Long endTime;
    /**
     * 秒杀产生的随机码，防止恶意攻击
     */
    private String randomCode;
}
