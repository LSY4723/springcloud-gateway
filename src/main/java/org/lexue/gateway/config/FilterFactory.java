package org.lexue.gateway.config;

import org.lexue.gateway.filter.CustomRewriteRouteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  @ClassName FilterFactory
 *  @Author ShuYu Liu
 *  @Description 过滤工厂
 *  @Date 2020/6/20 15:52
 */
@Configuration
public class FilterFactory {
    @Bean()
    public CustomRewriteRouteFilter buildCustomRewriteRouteFilter() {
        return new CustomRewriteRouteFilter();
    }
}
