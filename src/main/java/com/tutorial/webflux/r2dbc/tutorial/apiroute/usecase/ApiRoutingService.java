package com.tutorial.webflux.r2dbc.tutorial.apiroute.usecase;

import com.tutorial.webflux.r2dbc.tutorial.apiroute.data.ApiRoute;
import com.tutorial.webflux.r2dbc.tutorial.apiroute.data.ApiRouteRepository;
import com.tutorial.webflux.r2dbc.tutorial.apiroute.web.ApiRouteResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ApiRoutingService {

    private ApiRouteRepository apiRouteRepository;

    public ApiRoutingService(ApiRouteRepository apiRouteRepository){
        this.apiRouteRepository = apiRouteRepository;
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

    public Flux<ApiRoute> getAllRoutes(){
        return apiRouteRepository.findAll();
    }

    public Mono<ApiRoute> getRoute(Long id){
        return apiRouteRepository.findById(id);
    }

    public Mono<ApiRoute> getRouteByRouteId(String routeId){
        return apiRouteRepository.findApiRouteByRouteId(routeId);
    }

    public Mono<Void> updateRoute(ApiRoute apiRoute){
        return apiRouteRepository
                .save(apiRoute)
                .then();
    }

    public Mono<ApiRouteResponse> updateRouteAndSelect(ApiRoute apiRoute){
        return apiRouteRepository
                .save(apiRoute)
                .map(this::apiRoutesConvertToApiRouteResponse);
    }

    public Mono<Void> addRoute(ApiRoute apiRoute){
        return apiRouteRepository
                .save(apiRoute)
                .then();
    }

    public Mono<ApiRouteResponse> addRouteAndGet(ApiRoute apiRoute){
        return apiRouteRepository
                .save(apiRoute)
                .map(this::apiRoutesConvertToApiRouteResponse);
    }


    public Mono<Void> deleteRoute(String routeId){
        return apiRouteRepository.deleteApiRouteByRouteId(routeId);
    }

}
