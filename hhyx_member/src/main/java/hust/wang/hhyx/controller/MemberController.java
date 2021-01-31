package hust.wang.hhyx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hust.wang.hhyx.feign.SsoService;
import hust.wang.hhyx.service.MemberService;
import hust.wang.hhyx.entity.member.Member;
import hust.wang.hhyx.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangmh
 * @Date 2021/1/14 9:07 上午
 **/
@RestController
@RequestMapping(value = "member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @PostMapping(value = "/addMember")
    public R addMember(@RequestBody Member member, HttpServletRequest request){
        int addStatus = memberService.addMember(member);
        if (addStatus != 0){
            RestTemplate restTemplate = new RestTemplate();
            Map<String,String> map = new HashMap<>();
            map.put("nickname", member.getNickName());
            map.put("phone",member.getPhone());
            map.put("openId",member.getOpenId());
            ResponseEntity<R> forEntity = restTemplate.getForEntity("http://hhyx.com:15000/api/sso/createToken?nickname={nickname}&phone={phone}&openId={openId}",R.class,map);
            //没有被if条件拦截，就放行
            String code = forEntity.getStatusCode().toString();
            if(code.equals("200 OK")){
                R body = forEntity.getBody();
                String accessToken = body.getData().get("accessToken").toString();
                return R.ok().message("增加用户成功").data("accessToken",accessToken);
            }else{
                return R.error().message("增加凭证失败");
            }
        }else{
            return R.error().message("保存用户失败");
        }
    }

    @PostMapping(value = "/getMember")
    public R getMember(@RequestBody Member member){
        Member member1 = memberService.getMember(member);
        return R.ok().data("member",member1);
    }
}
