package com.mfsys.filter;

import com.mfsys.comm.constant.EurekaRegisteredMicroServicesURI;
import com.mfsys.comm.constant.SecurityURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @LoadBalanced
    private final WebClient webClient;
    private final RouteValidator validator;

    @Autowired
    public AuthenticationFilter(WebClient.Builder webClientBuilder, RouteValidator validator) {
        super(Config.class);
        this.webClient = webClientBuilder.baseUrl(EurekaRegisteredMicroServicesURI.SECURITY_SERVICE_LB).build();
        this.validator = validator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                return webClient.post().uri(SecurityURI.GENERATE_INTERNAL_TOKEN_URI).bodyValue(authHeader).retrieve()
                        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse
                                -> Mono.error(new RuntimeException("Response has error status."))).bodyToMono(String.class)
                        .flatMap(responseToken -> {
                            if (responseToken != null && !responseToken.isEmpty()) {
                                exchange.getRequest().mutate().header("Authorization", "Bearer " + responseToken).build();
                                return chain.filter(exchange);
                            } else {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                            }
                        });
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}
