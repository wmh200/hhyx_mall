package hust.wang.hhyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hust.wang.hhyx.entity.member.Member;

/**
 * @Author wangmh
 * @Date 2021/1/14 9:09 上午
 **/
public interface MemberService extends IService<Member> {
    int addMember(Member member);

    Member getMember(Member member);
}
