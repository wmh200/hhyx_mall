package hust.wang.hhyx.entity.member.vo;

import lombok.Data;

/**
 * @Author wangmh
 * @Date 2021/1/24 2:07 下午
 **/
@Data
public class MemberVo {
    private String nickName;
    private String phone;
    private Integer gender;
    private String city;
    private String country;
    private String avatarUrl;
    private Integer status;
    private String openId;
    private String language;

}
