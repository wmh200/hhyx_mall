package hust.wang.hhyx.entity.seckill;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author wangmh
 * @Date 2021/1/29 7:11 下午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_seckill_detail")//指定表名
public class SeckillDetail {
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
}
