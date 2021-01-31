//package hust.wang.hhyx.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//
//import io.seata.rm.datasource.DataSourceProxy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//
//import javax.sql.DataSource;
//
///**
// * @Author wangmh
// * @Date 2021/1/26 10:09 下午
// **/
//@Configuration
//public class MySeataConfig {
//    @Autowired
//    DataSourceProperties dataSourceProperties;
//    @Bean
//    public DataSource dataSource(DataSourceProperties dataSourceProperties){
//        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//        if (StringUtils.hasText(dataSourceProperties.getName())){
//            dataSource.setPassword(dataSourceProperties.getName());
//        }
//        return new DataSourceProxy(dataSource);
//    }
//}
