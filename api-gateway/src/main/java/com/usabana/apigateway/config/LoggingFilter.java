package com.usabana.apigateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class LoggingFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Bean
    public GlobalFilter logFilter() {
        return (exchange, chain) -> {

            String path = exchange.getRequest().getURI().getPath();
            String method = exchange.getRequest().getMethod().name();

            log.info("Gateway request: {} {}", method, path);

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() ->
                            log.info("Gateway response: {}", exchange.getResponse().getStatusCode())
                    ));
        };
    }
}