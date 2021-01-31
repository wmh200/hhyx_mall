package hust.wang.hhyx.gateway.filter;

import hust.wang.hhyx.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author wangmh
 * @Date 2021/1/18 10:23 上午
 **/
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("UrlFilter开始............");
        String uri = exchange.getRequest().getURI().toString();
        if(uri.contains("http://127.0.0.1:10000/api/member")) {
            return chain.filter(exchange);
        }
        String accessToken = exchange.getRequest().getHeaders().get("accessToken").toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", accessToken);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<R> forEntity = restTemplate.postForEntity("http://hhyx.com:15000/api/sso/verify",httpEntity,R.class);
        //没有被if条件拦截，就放行
        R r = forEntity.getBody();

        if(r.getCode() != 20000){
            log.info(r.getMsg());
            return Mono.defer(() -> {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);//设置status
                final ServerHttpResponse response = exchange.getResponse();
                byte[] bytes = "{\"code\":\"99000\",\"message\":\"权限校验失败，请重新登录~~~~~~\"}".getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                response.getHeaders().set("permValid", "false");//设置header
                log.info("权限校验失败，请重新登录~~~~~~");
                return response.writeWith(Flux.just(buffer));//设置body
            });

        }
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
