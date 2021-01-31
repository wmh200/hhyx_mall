package hust.wang.shopkeeper;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import hust.wang.hhyx.entity.shopkeeper.ShopKeeper;
import hust.wang.hhyx.stock.listener.ShopKeeperListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author wangmh
 * @Date 2021/1/20 2:10 下午
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShopkeeperTest {

    @Test
    public void saveTuanzhang() {
        String filePath = "/Users/wangmh/Desktop/chengduTZ.xlsx";
        ShopKeeperListener shopKeeperListener = new ShopKeeperListener();
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(filePath, ShopKeeper.class, shopKeeperListener);
        ExcelReaderSheetBuilder sheet = excelReaderBuilder.sheet();
        sheet.doRead();
    }


}
