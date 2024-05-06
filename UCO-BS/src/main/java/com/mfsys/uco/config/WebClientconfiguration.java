package com.mfsys.uco.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientconfiguration {

    @Value("${base.ciihive}")
    private String ciihive;

    @LoadBalanced
    @Bean
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClientDeposit() {
//        return WebClient.create("http://localhost:9095");
        return WebClient.create(ciihive);
    }

    @Bean
    public WebClient webClientCrm() {
//        return WebClient.create(ciihive);
        return WebClient.create("http://localhost:9096");
    }

}


