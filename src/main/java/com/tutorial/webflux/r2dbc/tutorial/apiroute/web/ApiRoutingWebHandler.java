package com.tutorial.webflux.r2dbc.tutorial.apiroute.web;

import com.tutorial.webflux.r2dbc.tutorial.apiroute.data.ApiRoute;
import com.tutorial.webflux.r2dbc.tutorial.apiroute.usecase.ApiRoutingService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class ApiRoutingWebHandler {

    private ApiRoutingService apiRoutingService;

    public ApiRoutingWebHandler(ApiRoutingService apiRoutingService){
        this.apiRoutingService = apiRoutingService;
    }

    public ApiRouteResponse apiRoutesConvertToApiRouteResponse(ApiRoute apiRoute){
        return ApiRouteResponse.builder()
                .id(apiRoute.getId())
                .path(apiRoute.getPath())
                .uri(apiRoute.getUri())
                .weightGroup(apiRoute.getWeightGroup())
                .weights(apiRoute.getWeights())
                .routeId(apiRoute.getRouteId())
                .build();
    }

    public Mono<ServerResponse> getRoutes(ServerRequest serverRequest){
        return apiRoutingService.getAllRoutes()
                .map(this::apiRoutesConvertToApiRouteResponse)
                .collectList()
                .flatMap(apiRouteResponse -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(apiRouteResponse);
                });
    }

}
