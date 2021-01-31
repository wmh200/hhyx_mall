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
 * @Date 2021/1/24 8:56 上午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_stock")//指定表名
public class Stock {
    @TableId(type = IdType.ID_WORKER)
    @TableField(value = "id")
    private Long id;
    @TableField(value = "sgu_id")
    public String sguId;
    @TableField(value = "sgu_name")
    public String sguName;
    @TableField(value = "sgu_total")
    public Integer sguTotal;
    @TableField(value = "sgu_stock")
    public Integer sguStock;
    @TableField(value = "sgu_sale")
    public Integer sguSale;
    @TableField(value = "sgu_status")
    public Integer sguStatus = 1;
    @TableField(value = "sgu_occupy")
    public Integer sguOccupy = 0;
    @TableField(value = "sgu_delivery")
    public Integer sguDelivery = 0;
}
