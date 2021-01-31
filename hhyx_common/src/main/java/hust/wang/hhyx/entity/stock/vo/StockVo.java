package hust.wang.hhyx.entity.stock.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author wangmh
 * @Date 2021/1/24 9:10 上午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockVo {
    public String SguId;
    public String SguName;
    public Integer SguSale;
    public Integer sguStock;
}
