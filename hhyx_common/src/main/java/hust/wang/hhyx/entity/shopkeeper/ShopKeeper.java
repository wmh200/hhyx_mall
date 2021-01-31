package hust.wang.hhyx.entity.shopkeeper;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author wangmh
 * @Date 2021/1/20 11:48 上午
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_shopkeeper")//指定表名
public class ShopKeeper {
    @ExcelProperty(index = 0)
    public String storeId;

    @ExcelProperty(index = 1)
    public String address;

    @ExcelProperty(index = 2)
    public String nickname;

    @ExcelProperty(index = 3)
    public String avatar;

    @TableField("residential_phone")
    @ExcelProperty(index = 4)
    public String residentialPhone;

    @TableField("residential_name")
    @ExcelProperty(index = 5)
    public String residentialName;

    @ExcelProperty(index = 6)
    public String detailedAddress;

    @ExcelProperty(index = 7)
    public Double lng;

    @ExcelProperty(index = 8)
    public Double lat;

    @ExcelProperty(index = 9)
    public String cityId;

    @ExcelProperty(index = 10)
    public String cityName;

    @ExcelProperty(index = 11)
    public String provinceId;

    @ExcelProperty(index = 12)
    public String provinceName;

    @ExcelProperty(index = 13)
    public Double distance;
}
