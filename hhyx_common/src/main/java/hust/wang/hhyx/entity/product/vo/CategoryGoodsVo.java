package hust.wang.hhyx.entity.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/6 10:05 下午
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryGoodsVo {
    private String categoryId;
    private String categoryName;
    private String iconUrl;
    private List<GoodVo> children;
}
