package hust.wang.hhyx.emun;

import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * @Author wangmh
 * @Date 2021/1/24 3:29 下午
 **/

public enum  OrderStatusEnum {
    CREATE_NEW(0,"待付款"),
    SEND_NEED(1,"待发货"),
    RECEIVE_NEED(2,"待收货"),
    RECEIVE_FINISHED(3,"已收货"),
    CANCELED(4,"已取消");
    private Integer code;
    private String msg;
    OrderStatusEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

}
