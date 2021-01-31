package hust.wang.hhyx.entity.product.vo;

import hust.wang.hhyx.entity.product.Good;
import hust.wang.hhyx.entity.seckill.vo.SeckillRespVo;
import lombok.Data;

/**
 * @Author wangmh
 * @Date 2021/1/30 3:09 下午
 **/
@Data
public class GoodRespVo {
    private Good good;
    private SeckillRespVo seckillRespVo;
}
