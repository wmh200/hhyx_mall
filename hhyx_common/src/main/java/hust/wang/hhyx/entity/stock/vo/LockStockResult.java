package hust.wang.hhyx.entity.stock.vo;

import lombok.Data;

/**
 * @Author wangmh
 * @Date 2021/1/25 1:54 下午
 **/
@Data
public class LockStockResult {
    private String skuId;
    private Integer num;
    private Boolean locked;
}
