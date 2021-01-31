package hust.wang.hhyx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hust.wang.hhyx.entity.member.Member;
import hust.wang.hhyx.mapper.MemberMapper;
import hust.wang.hhyx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author wangmh
 * @Date 2021/1/14 9:09 上午
 **/
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public int addMember(Member member) {
        if (null != member){
            int insert = 0;
            String openId = member.getOpenId();
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("open_id",openId);
            Member member1 = memberMapper.selectOne(queryWrapper);
            member.setUpdateTime(new Date());
            if (null == member1){
                member.setCreateTime(new Date());
                insert = memberMapper.insert(member);
            }else{
                memberMapper.update(member,null);
                insert = 2;
            }
            return insert;
        }
        return 0;
    }

    @Override
    public Member getMember(Member member) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        if (member.getNickName() != null){
            queryWrapper.eq("nickname",member.getNickName());
        }
        if (member.getPhone() != null){
            queryWrapper.eq("phone",member.getPhone());
        }
        Member member1 = memberMapper.selectOne(queryWrapper);
        return member1;
    }
}
