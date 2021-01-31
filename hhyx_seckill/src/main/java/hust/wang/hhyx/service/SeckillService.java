package hust.wang.hhyx.service;

import hust.wang.hhyx.entity.seckill.vo.SeckillRespVo;
import hust.wang.hhyx.entity.seckill.vo.SeckillVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/29 8:20 下午
 **/
public interface SeckillService {
    void uploadSeckillSguLatest3Days();

    List<SeckillRespVo> getCurrentSeckillSkus();

    SeckillVo getSguSeckillInfo(String sguId);

    String kill(String killId, String key, Integer num, HttpServletRequest request);
}
