package hust.wang.hhyx.aliyun.controller;


import hust.wang.hhyx.aliyun.service.MsmService;
import hust.wang.hhyx.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangmh
 * @Date 2021/1/13 4:45 下午
 **/
@RestController
@RequestMapping("third/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @GetMapping("sendCode")
    public R sendCode(@RequestParam(value = "phone") String phone,@RequestParam(value = "code") String code){
        msmService.send(phone,code);
        return R.ok();
    }

}
