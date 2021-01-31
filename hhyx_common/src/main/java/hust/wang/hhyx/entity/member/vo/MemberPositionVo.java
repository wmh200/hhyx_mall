package hust.wang.hhyx.entity.member.vo;

import lombok.Data;

/**
 * @Author wangmh
 * @Date 2021/1/24 11:44 下午
 **/
@Data
public class MemberPositionVo {
    private String province;
    private String city;
    private String district;
    private String latitude;
    private String longitude;
    private String address;
}
