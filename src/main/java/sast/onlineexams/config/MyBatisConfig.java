package sast.onlineexams.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author sherman
 * @create 2021-07-24 21:38
 * @description MyBatis配置类
 */

@Configuration
@MapperScan("sast.onlineexams.mbg.mapper")
public class MyBatisConfig {
}
