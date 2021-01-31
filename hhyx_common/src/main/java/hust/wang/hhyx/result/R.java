package hust.wang.hhyx.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangmh
 * @Date 2021/1/7 9:52 上午
 **/
@AllArgsConstructor
@Data
public class R {
    private Boolean success;

    private Integer code;

    private String msg;

    private Map<String, Object> data = new HashMap<String, Object>();

    R(){
    }

    public static R ok(){
        R r = new R();
        r.setCode(CodeMsg.COMMON_SUCCESS.getCode());
        r.setMsg(CodeMsg.COMMON_SUCCESS.getMsg());
        r.setSuccess(CodeMsg.COMMON_SUCCESS.getSuccess());
        return r;
    }

    public static R error(){
        R r = new R();
        r.setCode(CodeMsg.SERVER_EXCEPTION.getCode());
        r.setMsg(CodeMsg.SERVER_EXCEPTION.getMsg());
        r.setSuccess(CodeMsg.SERVER_EXCEPTION.getSuccess());
        return r;
    }

    public static R error(Boolean success, String msg, Integer code){
        R r = new R();
        r.setCode(code);
        r.setSuccess(success);
        r.setMsg(msg);
        return r;
    }

    public static R setResult(CodeMsg codeMsg){
        R r = new R();
        r.setSuccess(codeMsg.getSuccess());
        r.setCode(codeMsg.getCode());
        r.setMsg(codeMsg.getMsg());
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMsg(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
