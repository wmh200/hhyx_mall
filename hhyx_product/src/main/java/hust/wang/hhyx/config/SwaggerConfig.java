//package hust.wang.hhyx.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * @Author wangmh
// * @Date 2021/1/7 10:48 上午
// **/
//@Configuration
//@EnableSwagger2
//@ComponentScan("hust.wang.hhyx.controller")
//public class SwaggerConfig {
//    @Bean
//    public Docket docket(){
//        ApiInfo apiInfo = new ApiInfoBuilder().title("Swagger使用")
//                .description("Swagger使用示例")
//                .version("1.0")
//                .build();
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("hust.wang.hhyx.springbootswagger"))  //只扫描指定包下api
//                .paths(PathSelectors.any()) //通过api的uri过滤
//                .build();
//    }
//}
