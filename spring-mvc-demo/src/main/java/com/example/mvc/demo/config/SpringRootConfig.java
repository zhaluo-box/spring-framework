package com.example.mvc.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * springContext 中相关的Bean
 * 配置扫描除了controller包 之外的Bean
 * Created  on 2022/7/31 15:15:45
 *
 * @author zl
 */
@Configuration
@ComponentScan("com.example.mvc.demo.service")
public class SpringRootConfig {
}
