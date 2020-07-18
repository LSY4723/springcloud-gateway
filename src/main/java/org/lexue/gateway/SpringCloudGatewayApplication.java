package org.lexue.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
  *@ClassName SpringCloudGatewayApplication
  *@Description TODO
  *@Author ShuYu Liu
  *@Date 2020/6/20 18:06
  *@Version 1.0
  **/
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }
}
