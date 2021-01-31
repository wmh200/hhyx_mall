package hust.wang.hhyx.stock.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hust.wang.hhyx.entity.shopkeeper.ShopKeeper;
import hust.wang.hhyx.mapper.ShopperMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/20 1:44 下午
 **/
public class ShopKeeperListener extends AnalysisEventListener<ShopKeeper> {
    List<ShopKeeper> list = new ArrayList<>();
    @Autowired
    private ShopperMapper shopperMapper;
    @Override
    public void invoke(ShopKeeper shopKeeper, AnalysisContext analysisContext) {
        list.add(shopKeeper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("操作完成");
    }

    public List<ShopKeeper> getData(){
        return list;
    }
}
