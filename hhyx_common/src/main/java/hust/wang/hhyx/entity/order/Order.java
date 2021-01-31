package hust.wang.hhyx.entity.order;

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
 * @Date 2021/1/23 11:08 下午
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hhyx_order")//指定表名
public class Order {
    /**
     * id
     */
    @TableId(type = IdType.UUID)
    public String id;
    /**
     * 用户id
     */
    public String memberOpenid;
    /**
     * 订单号
     */
    public String orderSn;
    /**
     * 使用的优惠券
     */
    public String coupon_id;
    /**
     * 订单创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public Date createTime;
    /**
     * 用户名
     */
    public String memberUsername;
    /**
     * 订单总额
     */
    public Double totalAmount;
    /**
     * 应付总额
     */
    public Double payAmount;
    /**
     * 促销金额
     */
    public Double promotionAmount;
    /**
     * 优惠券抵消金额
     */
    public Double couponAmount;
    /**
     * 后台调整订单使用的折扣金额
     */
    public Double discountAmount;
    /**
     * 1->支付宝 2->微信 3->银联
     */
    public Integer payType;
    /**
     * 1->小程序 2->app
     */
    public Integer sourceType;
    /**
     * 订单状态：1.待付款。2.待发货。3.待收货。4.已收货
     */
    public Integer status;
    /**
     * 物流公司
     */
    public String deliveryCompany;
    /**
     * 物流单号
     */
    public String deliverySn;
    /**
     * 自动确认时间(天)
     */
    public int autoConfirmDay;
    /**
     * 收货人姓名
     */
    public String recieverName;
    /**
     * 收货人电话
     */
    public String recieverPhone;
    /**
     * 收货人省份
     */
    public String recieverProvince;
    /**
     * 收货人城市
     */
    public String recieverCity;
    /**
     * 收货人地区
     */
    public String recieverRegion;
    /**
     * 收货人详细地址
     */
    public String recieverDetailAddress;
    /**
     * 团长id
     */
    public String keeperId;
    /**
     * 团长电话
     */
    public String keeperPhone;
    /**
     * 团长名字
     */
    public String keeperName;
    /**
     * 团长地址
     */
    public String keeperAddress;
    /**
     * 订单备注
     */
    public String note;
    /**
     *确认收货状态 0：未确认 1：已确认
     */
    public Integer confirmStatus;
    /**
     * 订单删除状态：0：未删除 1：已删除
     */
    public Integer deleteStatus;
    /**
     * 下单使用的余额
     */
    public Double useBalance;
    /**
     * 订单支付时间
     */
    @DateTimeFormat(pattern="yyyy-MM HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public Date paymentTime;
    /**
     * 发货时间
     */
    @DateTimeFormat(pattern="yyyy-MM HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public Date deliveryTime;
    /**
     * 确认收货时间
     */
    @DateTimeFormat(pattern="yyyy-MM HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public Date recieveTime;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern="yyyy-MM HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public Date modifyTime;

}
