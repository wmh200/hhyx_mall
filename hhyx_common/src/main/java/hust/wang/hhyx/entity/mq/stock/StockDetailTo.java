package hust.wang.hhyx.entity.mq.stock;

import lombok.Data;

/**
 * @Author wangmh
 * @Date 2021/1/27 4:21 下午
 **/
@Data
public class StockDetailTo {
    private Integer id;

    /**
     * 商品id
     */
    private String sguId;

    /**
     * 商品名称
     */
    private String sguName;

    /**
     * 商品数量
     */
    private Integer sguNum;

    /**
     * 工作单id
     */
    private String taskId;

    /**
     * 1-锁定 2-解锁 3-库存扣减
     */
    private Integer lockStatus;
}
