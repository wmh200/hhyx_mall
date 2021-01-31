package hust.wang.hhyx.entity.member;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author wangmh
 * @Date 2021/1/14 9:10 上午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_member")//指定表名
public class Member {
    @TableId(type = IdType.UUID)
    public String id;
    @TableField(value = "nickname")
    public String nickName;
    public String phone;
    public Integer gender;
    public String city;
    public String province;
    public String country;
    public String avatarUrl;
    public Integer status;
    public String openId;

    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern="yyyy-MM HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM HH:mm:ss",timezone = "GMT+8")
    public Date createTime;

    @DateTimeFormat(pattern="yyyy-MM HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM HH:mm:ss",timezone = "GMT+8")
    @TableField(fill =FieldFill.INSERT_UPDATE)
    public Date updateTime;
}
