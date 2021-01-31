package hust.wang.hhyx.service.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hust.wang.hhyx.entity.member.MemberDelivery;
import hust.wang.hhyx.mapper.MemberDeliveryMapper;
import hust.wang.hhyx.service.MemberDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wangmh
 * @Date 2021/1/24 10:07 下午
 **/
@Service
public class MemberDeliveryServiceImpl extends ServiceImpl<MemberDeliveryMapper, MemberDelivery> implements MemberDeliveryService {
    @Autowired
    MemberDeliveryMapper memberDeliveryMapper;

    @Override
    public int addMemberDelivery(MemberDelivery memberDelivery) {
        int insert = 0;
        insert = memberDeliveryMapper.insert(memberDelivery);
        return insert;
    }
}
