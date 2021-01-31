package hust.wang.hhyx.entity.product;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author wangmh
 * @Date 2021/1/6 8:24 下午
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_category")//指定表名
@Builder
@ApiModel("品类")  //对实体进行描述
public class Category {
    @ExcelProperty(index = 0)
    @TableField(value = "category_id")
    @ApiModelProperty("主键")  //对实体属性进行描述
    public String categoryId;

    @ExcelProperty(index = 1)
    @TableField(value = "category_name")
    public String categoryName;

    @ExcelProperty(index = 2)
    @TableField(value = "icon_url")
    public String iconUrl;

    @ExcelProperty(index = 3)
    @TableField(value = "category_type")
    public int categoryType;

    @ExcelProperty(index = 4)
    @TableField(value = "total")
    public int total;
}
