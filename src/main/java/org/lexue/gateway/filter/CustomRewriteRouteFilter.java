package org.lexue.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetSocketAddress;
import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 *  @ClassName CustomRewriteRouteFilter
 *  @Author ShuYu Liu
 *  @Description 重置url
 *  @Date 2020/6/20 15:27
 */
public class CustomRewriteRouteFilter extends AbstractGatewayFilterFactory {
    private static final Log log = LogFactory.getLog(CustomRewriteRouteFilter.class);
    
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            //获取url地址
            ServerHttpRequest request = exchange.getRequest();
            String rawPath = request.getURI().getRawPath();
            //请求头
            HttpHeaders headers = request.getHeaders();
            //请求方法
            HttpMethod method = request.getMethod();
            //请求参数
            MultiValueMap < String, String > queryParams = request.getQueryParams();
            String appid = headers.get("appid").get(0);
            URI uri;
            boolean isTrue = appid.equals("a1");
            log.info(isTrue);
            if (isTrue){
               log.info("201");
                uri = UriComponentsBuilder.fromHttpUrl("http://10.10.200.201:10000"+rawPath).queryParams(queryParams).build().toUri();
            }else if (appid.equals("a2")){
                log.info("211");
                uri = UriComponentsBuilder.fromHttpUrl("http://10.10.200.211:10000"+rawPath).queryParams(queryParams).build().toUri();
            }else {
                throw new NullPointerException("找不到");
            }
            //替换新的url地址
            ServerHttpRequest serverHttpRequest = request.mutate().uri(uri).method(method).headers(
                httpHeaders -> httpHeaders=headers).build();
            Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
            //从新设置Route地址
            Route newRoute =
                Route.async().asyncPredicate(route.getPredicate()).filters(route.getFilters()).id(route.getId())
                    .order(route.getOrder()).uri(uri).build();
            exchange.getAttributes().put(GATEWAY_ROUTE_ATTR,newRoute);
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        };
    }
}
