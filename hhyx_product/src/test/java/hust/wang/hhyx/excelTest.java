package hust.wang.hhyx;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import hust.wang.hhyx.entity.product.Category;
import hust.wang.hhyx.entity.product.Good;

import hust.wang.hhyx.mapper.CategoryMapper;
import hust.wang.hhyx.mapper.GoodMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2020/12/29 3:08 下午
 **/

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class excelTest {
    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void readGoods() {
        //读取历史数据
        String filePath = "/Users/wangmh/Desktop/goodXHT.xlsx";
        PutDataService  putDataService = new PutDataService();
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(filePath, Good.class,putDataService);
        ExcelReaderSheetBuilder sheet = excelReaderBuilder.sheet();
        sheet.doRead();
        List<Good> goods = putDataService.getData();
        goods.stream().forEach( good -> {
            goodMapper.insert(good);
        });
    }

    @Test
    public void readCategory() {
        //读取历史数据
        String filePath = "/Users/wangmh/Desktop/category.xlsx";
        PutCategoryService  putCategoryService = new PutCategoryService();
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(filePath, Category.class,putCategoryService);
        ExcelReaderSheetBuilder sheet = excelReaderBuilder.sheet();
        sheet.doRead();
        List<Category> categories = putCategoryService.getDatas();
        categories.stream().forEach( category -> {
            categoryMapper.insert(category);
        });
    }

}
