package hust.wang.hhyx.controller;

import hust.wang.hhyx.util.RedisUtil;
import hust.wang.hhyx.result.CodeMsg;
import hust.wang.hhyx.result.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author wangmh
 * @Date 2021/1/18 9:13 上午
 **/
@RestController
@RequestMapping("sso")
public class SsoController {

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping(value = "verify")
    public R verifyToken(HttpServletRequest request){
        String accessToken = request.getHeader("accessToken").replace("[","").replace("]","");
        if(!StringUtils.isEmpty(accessToken)){
            String s = redisUtil.get(accessToken);
            if(!StringUtils.isEmpty(s)){
                return R.ok().message("验证成功");
            }else {
                return R.setResult(CodeMsg.LOGIN_TIMEOUT_EXCEPTION);
            }
        }else{
            return R.setResult(CodeMsg.LOGIN_TIMEOUT_EXCEPTION);
        }
    }

    @GetMapping(value = "createToken")
    public R createToken(@RequestParam("nickname") String nickname,
                         @RequestParam("phone") String phone,
                         @RequestParam("openId") String openId,
                         HttpServletResponse response){
        String token = UUID.randomUUID().toString().replace("-","");
        String userInfo = nickname+"-"+phone+"-"+openId;
        redisUtil.setEx(token,userInfo,1200, TimeUnit.MINUTES);
        Cookie cookie = new Cookie("accessToken",token);
        response.addCookie(cookie);
        return R.ok().data("accessToken",token);
    }

    @GetMapping("haha")
    public String haha(){
        return "haha";
    }

}
