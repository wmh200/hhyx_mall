package hust.wang.hhyx.entity.product.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author wangmh
 * @Date 2021/1/6 10:09 下午
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodVo {
    public String sguId;
    public String sguName;
    public String mainImageUrl;
    public Double salePrice;
    public Double marketPrice;
    public int totalSales;
}
