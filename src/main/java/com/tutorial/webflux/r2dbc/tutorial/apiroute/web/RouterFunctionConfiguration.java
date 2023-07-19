package com.tutorial.webflux.r2dbc.tutorial.apiroute.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouterFunctionConfiguration {

    private ApiRoutingWebHandler apiRoutingWebHandler;

    public RouterFunctionConfiguration(ApiRoutingWebHandler apiRoutingWebHandler){
        this.apiRoutingWebHandler = apiRoutingWebHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/api-routes",
                        accept(MediaType.APPLICATION_JSON),
                        apiRoutingWebHandler::getRoutes)
                .build();
    }

}
