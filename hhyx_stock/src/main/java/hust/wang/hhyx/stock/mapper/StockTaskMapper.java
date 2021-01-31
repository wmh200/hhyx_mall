package hust.wang.hhyx.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hust.wang.hhyx.entity.stock.StockTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author wangmh
 * @Date 2021/1/27 3:15 下午
 **/
@Repository
public interface StockTaskMapper extends BaseMapper<StockTask> {
}
