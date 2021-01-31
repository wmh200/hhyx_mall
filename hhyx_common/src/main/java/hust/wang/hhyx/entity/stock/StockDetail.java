package hust.wang.hhyx.entity.stock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author wangmh
 * @Date 2021/1/27 3:02 下午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_stock_task_detail")//指定表名
public class StockDetail {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
    private Integer taskId;

    /**
     * 1-锁定 2-解锁 3-库存扣减
     */
    private Integer lockStatus;
}
