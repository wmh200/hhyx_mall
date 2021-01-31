package hust.wang.hhyx.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * @Author wangmh
 * @Date 2020/12/29 3:19 下午
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_good")//指定表名
public class Goods {
    @TableField(value = "groupon_id")
    @ExcelProperty(index = 0)
    private String grouponId;

    @TableField(value = "merchandise_id")
    @ExcelProperty(index = 1)
    private String merchandiseId;

    @TableField(value = "merch_type_id")
    @ExcelProperty(index = 2)
    private String merchTypeId;

    @TableField(value = "title")
    @ExcelProperty(index = 3)
    private String title;

    @TableField(value = "content")
    @ExcelProperty(index = 4)
    private String content;

    @TableField(value = "item_image")
    @ExcelProperty(index = 5)
    private String itemImage;

    @TableField(value = "type_content")
    @ExcelProperty(index = 6)
    private String typeContent;

    @TableField(value = "origin_price")
    @ExcelProperty(index = 7)
    private Double originPrice;

    @TableField(value = "activity_price")
    @ExcelProperty(index = 8)
    private Double activityPrice;

    @TableField(value = "activity_id")
    @ExcelProperty(index = 9)
    private String activityId;

    @TableField(value = "limit_quantity")
    @ExcelProperty(index = 10)
    private Integer limitQuantity;

    @TableField(value = "quantity")
    @ExcelProperty(index = 11)
    private Integer quantity;

    @TableField(value = "water_quantity")
    @ExcelProperty(index = 12)
    private Integer waterQuantity;

    @TableField(value = "max_quantity")
    @ExcelProperty(index = 13)
    private Integer maxQuantity;

    @TableField(value = "categoryId_list")
    @ExcelProperty(index = 14)
    private String categoryIdList;

    @TableField(value = "deliver_day")
    @ExcelProperty(index = 15)
    private String deliverDay;

    @TableField(value = "deliver_daytime")
    @ExcelProperty(index = 16)
    private String deliverDaytime;

    @TableField(value = "supplier_name")
    @ExcelProperty(index = 17)
    private String supplierName;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ExcelProperty(index = 18)
    @TableField(value = "hot_sale_time")
    private String hotSaleTime;

    @TableField(value = "hot_cover_image")
    @ExcelProperty(index = 19)
    private String hotCoverImage;
}
