package hust.wang.hhyx.entity.vo;

import hust.wang.hhyx.entity.product.Good;
import lombok.Data;


import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/21 8:56 上午
 **/
@Data
public class Top5Products {
    public String category_id;

    public String category_name;

    public String category_image;

    public List<Good> goodList;
}
