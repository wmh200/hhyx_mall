package hust.wang.hhyx;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hust.wang.hhyx.entity.product.Good;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2020/12/29 3:22 下午
 **/
public class PutDataService extends AnalysisEventListener<Good> {
    List<Good> list = new ArrayList<>();
    @Override
    public void invoke(Good good, AnalysisContext analysisContext) {
        list.add(good);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("解析完成");
    }
    public List<Good> getData(){
        return list;
    }
}
