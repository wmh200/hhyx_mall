package hust.wang.hhyx.controller;
import hust.wang.hhyx.entity.member.MemberDelivery;
import hust.wang.hhyx.result.R;
import hust.wang.hhyx.service.MemberDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wangmh
 * @Date 2021/1/24 10:01 下午
 **/
@RestController
@RequestMapping("member/delivery")
public class MemberDeliveryController {
    @Autowired
    MemberDeliveryService memberDeliveryService;

    @PostMapping("/addMemberDelivery")
    public R addMemberDelivery(@RequestBody MemberDelivery memberDelivery){
        int i = memberDeliveryService.addMemberDelivery(memberDelivery);
        if(i == 1){
            return R.ok();
        }else{
            return R.error();
        }
    }

}
