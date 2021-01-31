package hust.wang.hhyx.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wangmh
 * @Date 2021/1/8 10:40 上午
 **/
@RestController
@RefreshScope
public class testController {

    @Value("${product.user.name}")
    private String username;

}
