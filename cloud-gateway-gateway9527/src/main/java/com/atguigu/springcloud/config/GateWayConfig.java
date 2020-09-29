package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {
   @Bean
   public RouteLocator customRouteLocator(RouteLocatorBuilder builder){

      return builder.routes()
              .route("path_route_atgg", r -> r.path("/guonei")
                      .uri("http://news.baidu.com/guonei")).build();
   }
}
