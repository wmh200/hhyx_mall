package hust.wang.hhyx.feign;

import hust.wang.hhyx.entity.member.MemberDelivery;
import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author wangmh
 * @Date 2021/1/25 1:23 下午
 **/
@FeignClient("hhyx-member")
public interface MemberFeignService {
    @PostMapping("api/member/delivery/addMemberDelivery")
    public R addMemberDelivery(@RequestBody MemberDelivery memberDelivery);
}
