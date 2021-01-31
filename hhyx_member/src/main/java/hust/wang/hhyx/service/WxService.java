package hust.wang.hhyx.service;

import org.apache.http.HttpException;

import java.io.IOException;

/**
 * @Author wangmh
 * @Date 2021/1/13 8:01 下午
 **/
public interface WxService {
    String getPhoneNum(String code) throws IOException, HttpException;
}
