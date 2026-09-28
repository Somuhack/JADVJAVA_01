package com.example.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            System.out.println(
                    "Gateway Request: "
                            + exchange.getRequest().getMethod()
                            + " "
                            + exchange.getRequest().getURI()
            );

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}
