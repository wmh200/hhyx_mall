package hust.wang.hhyx.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author wangmh
 * @Date 2021/1/23 11:53 下午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_order_item")//指定表名
public class OrderItem {
    @TableId(type = IdType.UUID)
    private String id;
    private String orderSn;
    private String sguId;
    private String sguName;
    private Integer sguCount;
    private Double sguPrice;
    private String sguPic;
    private String categoryId;
    private String categoryName;
    private Double promotionAmount;
    private Double couponAmount;
    private Double RealAmount;
}
