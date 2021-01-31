package hust.wang.hhyx.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wangmh
 * @Date 2021/1/23 1:52 下午
 **/
@Configuration
public class FeignConfig {
    /**
     * 解决feign远程调用丢失原请求的请求头信息  但是Feign异步情况加会丢失上下文的问题 因为异步是用不同的线程不能共享上下文信息
     *      解决：在请求拦截前先获取请求的请求上下文信息 然后每个线程重新set进去 RequestContextHolder.setRequestAttributes()
     *                 RequestContextHolder.setRequestAttributes()
     *
     *                 RequestContextHolder用的是threadLocal
     * @retur    */
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                String cookie = request.getHeader("accessToken");
                //给新请求加入header信息
                requestTemplate.header("accessToken",cookie);
            }
        };
    }
}
