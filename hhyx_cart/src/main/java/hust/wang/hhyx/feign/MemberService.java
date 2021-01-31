package hust.wang.hhyx.feign;

import hust.wang.hhyx.entity.member.Member;
import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author wangmh
 * @Date 2021/1/18 10:28 下午
 **/
@FeignClient(value = "hhyx-member")
public interface MemberService {
    @PostMapping(value = "api/member/getMember")
    public R getMember(@RequestBody Member member);
}
