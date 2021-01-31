package hust.wang.hhyx.entity.stock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author wangmh
 * @Date 2021/1/27 3:07 下午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_stock_task")//指定表名
public class StockTask {
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Integer id;

    private String orderId;

    private String orderSn;

    private String paymentWay;

}
