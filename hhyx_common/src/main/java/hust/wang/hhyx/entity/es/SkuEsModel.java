package hust.wang.hhyx.entity.es;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author wangmh
 * @Date 2021/1/31 2:29 下午
 **/
@Data
public class SkuEsModel {
    public String sguId;

    public String sguName;

    public Double salePrice;

    public Double marketPrice;

    public Long arrivalDateStr;

    public String mainImageUrl;

    public Integer stock;

    public Integer sellNum;

}
