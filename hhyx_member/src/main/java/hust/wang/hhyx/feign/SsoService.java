package hust.wang.hhyx.feign;

import hust.wang.hhyx.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author wangmh
 * @Date 2021/1/18 9:56 上午
 **/
@FeignClient(value = "hhyx-sso")
public interface SsoService {
    @GetMapping(value = "sso/createToken")
    public R createToken(@RequestParam("nickname") String nickname,
                         @RequestParam("phone") String phone);
}
