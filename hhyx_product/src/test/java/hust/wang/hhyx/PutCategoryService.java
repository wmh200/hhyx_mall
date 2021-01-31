package hust.wang.hhyx;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hust.wang.hhyx.entity.product.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/6 8:35 下午
 **/
public class PutCategoryService extends AnalysisEventListener<Category> {
    List<Category> list = new ArrayList<>();
    @Override
    public void invoke(Category category, AnalysisContext analysisContext) {
        list.add(category);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("解析完成");
    }

    public List<Category> getDatas(){
        return list;
    }
}
