package hust.wang.hhyx.service;


import com.baomidou.mybatisplus.extension.service.IService;
import hust.wang.hhyx.entity.product.Category;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/6 10:21 下午
 **/
public interface CategoryService extends IService<Category> {

    List<Category> getCategory();
}
