package hust.wang.hhyx.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hust.wang.hhyx.entity.stock.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author wangmh
 * @Date 2021/1/24 8:49 上午
 **/
@Repository
public interface StockMapper extends BaseMapper<Stock> {
    @Select("select * from hhyx_stock where sgu_id = #{skuId} and sgu_stock >= #{sguCount}")
    Stock selectOneByParams(@Param("skuId")String skuId,@Param("sguCount")int sguCount);

    @Update("update hhyx_stock set sgu_stock = sgu_stock " +
            "+ #{num},sgu_delivery = sgu_delivery - #{num} where sgu_id = #{skuId}")
    void updateStock(@Param("skuId")String skuId, @Param("num")int num);
}
