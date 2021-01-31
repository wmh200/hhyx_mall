package hust.wang.hhyx.aliyun.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import hust.wang.hhyx.aliyun.service.MsmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author wangmh
 * @Date 2021/1/13 4:54 下午
 **/
@Service
@Slf4j
public class MsmServiceImpl implements MsmService {

    @Override
    public void send(String phone, String code) {
        if(StringUtils.isEmpty(phone)){
            log.info("手机号不能为空");
            return;
        }else{
            log.info("阿里云短信验证");
//            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<accessKeyId>", "<accessSecret>");
//            IAcsClient client = new DefaultAcsClient(profile);
//
//            CommonRequest request = new CommonRequest();
//            request.setSysMethod(MethodType.POST);
//            request.setSysDomain("dysmsapi.aliyuncs.com");
//            request.setSysVersion("2017-05-25");
//            request.setSysAction("SendSms");
//            request.putQueryParameter("RegionId", "cn-hangzhou");
//
//            //设置发送的相关的参数
//            //1.设置手机号
//            request.putQueryParameter("PhoneNumbers",phone);
//            //2.申请阿里云签名名称
//            request.putQueryParameter("SignName","惠汇优选实践平台");
//            //3.申请阿里云 模板code
//            request.putQueryParameter("TemplateCode","SMS_191490010");
//            //4.验证码数据 转化为json格式
//            request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
//            try {
//                CommonResponse response = client.getCommonResponse(request);
//                boolean success = response.getHttpResponse().isSuccess();
//                System.out.println(response.getData());
//                return success;
//            } catch (ServerException e) {
//                e.printStackTrace();
//            } catch (ClientException e) {
//                e.printStackTrace();
//            }
        }
    }
}
