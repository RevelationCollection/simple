package com.simple.gateway.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configurable
public class ServerConfig {

    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        // @formatter:off
        return builder.routes()
                .route(r -> r.path("/fluent/service-hi/**")
                        .filters(f -> f.stripPrefix(2)
                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                        .uri("lb://SERVICE-HI")
                        .order(0)
                        .id("fluent_customer_service")
                )
                .build();
        // @formatter:on
    }
}
