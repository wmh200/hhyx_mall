package hust.wang.hhyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hust.wang.hhyx.entity.product.Category;
import hust.wang.hhyx.mapper.CategoryMapper;
import hust.wang.hhyx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/6 10:23 下午
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> getCategory() {
        List<Category> categories = categoryMapper.selectList(null);
        categories.remove(0);
        return categories;
    }
}
