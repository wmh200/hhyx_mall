package hust.wang.hhyx.entity.mq.stock;

import hust.wang.hhyx.entity.stock.StockDetail;
import lombok.Data;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/27 4:04 下午
 **/
@Data
public class StockLockedTo {
    /**
     * 库存工作单的id
     */
    private Integer id;

    /**
     * 工作单详情id
     */
    private StockDetailTo detail;
}
