package hust.wang.hhyx.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hust.wang.hhyx.entity.seckill.SeckillSession;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.SeckillSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author wangmh
 * @Date 2021/1/29 8:55 下午
 **/
@RequestMapping("coupon/seckill")
@RestController
public class SeckillSessionController {
    @Autowired
    SeckillSessionService seckillSessionService;

    @GetMapping("latest3DaySession")
    public R getLatest3DaySession(){
        List<SeckillSession> sessions = seckillSessionService.getLatest3DaySession();
        return R.ok().data("sessions", JSONArray.toJSONString(sessions));
    }

}
