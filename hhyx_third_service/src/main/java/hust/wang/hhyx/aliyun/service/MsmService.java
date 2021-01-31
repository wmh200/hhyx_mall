package hust.wang.hhyx.aliyun.service;

import java.util.Map;

/**
 * @Author wangmh
 * @Date 2021/1/13 4:45 下午
 **/
public interface MsmService {
    /**
     * 发送短信验证码
     * @param param
     * @param phone
     * @return
     */
    void send(String phone, String code);
}
