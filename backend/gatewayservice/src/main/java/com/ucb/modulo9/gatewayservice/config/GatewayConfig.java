package com.ucb.modulo9.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route for userservice general API
                .route("userservice-route", r -> r.path("/USERSERVICE/**")
                        .filters(f -> f.rewritePath("/USERSERVICE/(?<segment>.*)", "/${segment}"))
                        .uri("lb://USERSERVICE"))
                // Route for userservice Swagger UI
                .route("userservice-swagger", r -> r.path("/USERSERVICE/swagger-ui.html")
                        .filters(f -> f.rewritePath("/USERSERVICE/(?<segment>.*)", "/${segment}"))
                        .uri("lb://USERSERVICE"))
                // Route for agendaservice general API
                .route("agendaservice-route", r -> r.path("/AGENDASERVICE/**")
                        .filters(f -> f.rewritePath("/AGENDASERVICE/(?<segment>.*)", "/${segment}"))
                        .uri("lb://AGENDASERVICE"))
                // Route for agendaservice Swagger UI
                .route("agendaservice-swagger", r -> r.path("/AGENDASERVICE/swagger-ui.html")
                        .filters(f -> f.rewritePath("/AGENDASERVICE/(?<segment>.*)", "/${segment}"))
                        .uri("lb://AGENDASERVICE"))
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.setMaxAge(3600L);
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
