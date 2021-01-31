package hust.wang.hhyx.entity.product;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author wangmh
 * @Date 2021/1/6 8:10 下午
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_good")//指定表名
@ApiModel("商品")
public class Good {
    @TableField(value = "id")
    @ExcelProperty(index = 0)
    public String id;

    @TableField(value = "prod_id")
    @ExcelProperty(index = 1)
    public String prodId;

    @TableField(value = "prod_name")
    @ExcelProperty(index = 2)
    public String prodName;

    @TableField(value = "sgu_id")
    @ExcelProperty(index = 3)
    public String sguId;

    @TableField(value = "sgu_name")
    @ExcelProperty(index = 4)
    public String sguName;

    @TableField(value = "supply_number")
    @ExcelProperty(index = 5)
    public String supplyNumber;

    @TableField(value = "pn_name")
    @ExcelProperty(index = 6)
    public String pnName;

    @TableField(value = "category_two")
    @ExcelProperty(index = 7)
    public String categoryTwo;

    @TableField(value = "main_image_url")
    @ExcelProperty(index = 8)
    public String mainImageUrl;

    @TableField(value = "sale_price")
    @ExcelProperty(index = 9)
    public Double salePrice;

    @TableField(value = "market_price")
    @ExcelProperty(index = 10)
    public Double marketPrice;

    @TableField(value = "arrival_date")
    @ExcelProperty(index = 11)
    public Long arrivalDate;

    @TableField(value = "city_id")
    @ExcelProperty(index = 12)
    public String cityId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField(value = "arrival_date_str")
    @ExcelProperty(index = 13)
    public Date arrivalDateStr;

    @TableField(value = "prefix_name")
    @ExcelProperty(index = 14)
    public String prefixName;

    @TableField(value = "sellable_stock")
    @ExcelProperty(index = 15)
    public Integer sellableStock;

    @TableField(value = "big_image_url")
    @ExcelProperty(index = 16)
    public String bigImageUrl;

    @TableField(value = "total_sales")
    @ExcelProperty(index = 17)
    public int totalSales;

    @TableField(value = "category_three")
    @ExcelProperty(index = 18)
    public String categoryThree;

    @TableField(value = "category_three_name")
    @ExcelProperty(index = 19)
    public String categoryThreeName;

    @TableField(value = "images")
    @ExcelProperty(index = 20)
    public String images;

    @TableField(value = "category_id")
    @ExcelProperty(index = 21)
    public String categoryId;

    @TableField(value = "category_name")
    public String categoryName;

}

