package hust.wang.hhyx.result;

/**
 * @Author wangmh
 * @Date 2021/1/7 9:40 上午
 **/
public enum CodeMsg {
    COMMON_SUCCESS(true,20000,"返回成功"),

    SERVER_EXCEPTION(false,40001,"服务端异常"),

    SMS_CODE_EXCEPTION(false,40002,"短信验证码频率太高"),

    LOGIN_FAILED_EXCEPTION(false,40003,"用户登录失败"),

    LOGIN_TIMEOUT_EXCEPTION(false,40004,"用户登录凭证失效"),

    NO_STOCK_EXCEPTION(false,80001,"商品库存不足"),

    PRODUCT_UP_EXCEPTION(false,80002,"商品上架错误");

    private Boolean success;

    private Integer code;

    private String msg;

    CodeMsg(){
        this.code = 200;
        this.msg = "返回成功";
    }

    CodeMsg(boolean success,int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public boolean getSuccess() {
        if(success != null){
            return success;
        }
        return true;
    }
    public int getCode() {
        if(code != null) {
            return code;
        }
        return 20000;
    }

    public String getMsg() {
        if(msg != null) {
            return msg;
        }
        return "返回成功";
    }

}
