package hust.wang.hhyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hust.wang.hhyx.entity.seckill.SeckillSession;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/29 8:31 下午
 **/
public interface SeckillSessionService extends IService<SeckillSession> {
    List<SeckillSession> getLatest3DaySession();
}
