package com.atguigu.springcloud.filter;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
   @Override
   public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
      String uname=exchange.getRequest().getQueryParams().getFirst("username");
      if(StringUtils.isEmpty(uname))
      {
         exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
         return exchange.getResponse().setComplete();
      }

      return chain.filter(exchange);
   }

   @Override
   public int getOrder() {
      return 0;
   }
}
