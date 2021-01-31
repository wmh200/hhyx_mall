package hust.wang.hhyx.controller;
import hust.wang.hhyx.constant.AuthServerConstant;
import hust.wang.hhyx.feign.ThirdServiceService;
import hust.wang.hhyx.result.CodeMsg;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.util.RandomUtil;
import hust.wang.hhyx.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;
/**
 * @Author wangmh
 * @Date 2021/1/16 2:44 下午
 **/
@RestController
@RequestMapping(value = "member/login")
public class LoginController {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ThirdServiceService thirdServiceService;


    @GetMapping(value = "/sendCode")
    public R sendCode(@RequestParam(value = "phone") String phone){
        // 1.接口防刷
        String redisCode = redisUtil.get(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone);
        if(!StringUtils.isEmpty(redisCode)) {
            long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - l < 60 * 1000) {
                //60秒内不能再发
                return R.error(CodeMsg.SMS_CODE_EXCEPTION.getSuccess(),CodeMsg.SMS_CODE_EXCEPTION.getMsg(),CodeMsg.SMS_CODE_EXCEPTION.getCode());
            }
        }
        // 2.
        String code = RandomUtil.getSixBitRandom() + "_" + System.currentTimeMillis();
        redisUtil.setEx(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone,code,5, TimeUnit.MINUTES);

        thirdServiceService.sendCode(phone,code);
        return R.ok().data("code",code);
    }

    @PostMapping(value = "/verifyUser")
    public R verifyUser(@RequestParam(value = "phone")String phone,@RequestParam(value = "code")String code) {
        String redisCode = redisUtil.get(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone);
        if(!StringUtils.isEmpty(redisCode)) {
            String codeNum = redisCode.split("_")[0];
            if(codeNum.equals(code)){
                return R.ok();
            }else{
                System.out.println("hehheeh");
                return R.error().message("验证码错误");
            }

        }else {
            return R.error().message("注册超时");
        }
    }


}
