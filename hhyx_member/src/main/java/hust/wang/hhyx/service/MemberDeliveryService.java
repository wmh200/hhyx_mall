package hust.wang.hhyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hust.wang.hhyx.entity.member.MemberDelivery;
import org.springframework.stereotype.Service;

/**
 * @Author wangmh
 * @Date 2021/1/24 10:05 下午
 **/

public interface MemberDeliveryService extends IService<MemberDelivery> {
    /**
     * 将用户自提点信息加入表中
     * @param memberDelivery
     * @return
     */
    int addMemberDelivery(MemberDelivery memberDelivery);
}
