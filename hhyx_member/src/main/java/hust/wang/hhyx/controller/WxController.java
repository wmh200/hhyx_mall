package hust.wang.hhyx.controller;


import com.alibaba.excel.util.StringUtils;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.WxService;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author wangmh
 * @Date 2021/1/13 8:00 下午
 **/
@RestController
@RequestMapping(value = "member/wxlogin")
public class WxController {
    @Autowired
    private WxService wxService;

    @GetMapping (value = "/getPhoneNum")
    public R getPhoneNum(@RequestParam(value = "code") String code) throws IOException, HttpException {

        String openid = wxService.getPhoneNum(code);
        if (!StringUtils.isEmpty(openid)){
            return R.ok().data("openid",openid).message("获取openid成功");
        }else{
            return R.error().message("获取openid错误");
        }
    }
}
