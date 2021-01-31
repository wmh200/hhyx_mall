package hust.wang.hhyx.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hust.wang.hhyx.constant.WxLoginConstant;
import hust.wang.hhyx.util.HttpUtil;
import hust.wang.hhyx.service.WxService;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author wangmh
 * @Date 2021/1/13 8:01 下午
 **/
@Service
public class WxServiceImpl implements WxService {

    @Override
    public String getPhoneNum(String code) throws IOException, HttpException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        url = String.format(url, WxLoginConstant.APPID, WxLoginConstant.SECRET, code);
        String s = HttpUtil.sendGet(url);
        System.out.println(s);
        JSONObject jsonObject = JSON.parseObject(s);
        s = jsonObject.get("openid").toString();
        return s;
    }
}
