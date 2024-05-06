package com.mfsys.uco;

import com.mfsys.comm.constant.MicroserviceBaseURI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.mfsys"})
@EnableDiscoveryClient
public class UcoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(UcoApplication.class, args);
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setContextPath(MicroserviceBaseURI.UCO);
    }
}
