package hust.wang.hhyx.entity.member;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author wangmh
 * @Date 2021/1/24 9:53 下午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_member_delivery")//指定表名
public class MemberDelivery {
    private String id;
    private String memberOpenId;
    private String storeId;
    private String memberAddress;
    private String memberProvince;
    private String memberCity;
    private String memberDistrict;
    private String memberPhone;
}
