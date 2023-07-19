package com.tutorial.webflux.r2dbc.tutorial.apiroute.web;

import com.tutorial.webflux.r2dbc.tutorial.apiroute.data.ApiRoute;
import com.tutorial.webflux.r2dbc.tutorial.apiroute.usecase.ApiRoutingService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
public class ApiRoutingController {

    private ApiRoutingService apiRoutingService;

    public ApiRoutingController(ApiRoutingService apiRoutingService){
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

    public ApiRoute apiRouteRequestConverToApiRoute(Long id, ApiRouteRequest apiRouteRequest){
        return ApiRoute.builder()
                .id(id)
                .path(apiRouteRequest.getPath())
                .uri(apiRouteRequest.getUri())
                .weightGroup(apiRouteRequest.getWeightGroup())
                .weights(apiRouteRequest.getWeights())
                .routeId(apiRouteRequest.getRouteId())
                .build();
    }

    public ApiRoute apiRouteRequestConverToApiRoute(ApiRouteRequest apiRouteRequest){
        var apiRoute = new ApiRoute();
        apiRoute.setPath(apiRouteRequest.getPath());
        apiRoute.setUri(apiRouteRequest.getUri());
        apiRoute.setWeightGroup(apiRouteRequest.getWeightGroup());
        apiRoute.setWeights(apiRouteRequest.getWeights());
        apiRoute.setRouteId(apiRouteRequest.getRouteId());
        return apiRoute;
    }

//    @GetMapping(path = "/api-routes")
//    public Mono<List<ApiRouteResponse>> getRoutes(){
//        return apiRoutingService.getAllRoutes()
//                .map(this::apiRoutesConvertToApiRouteResponse)
//                .collectList()
//                .subscribeOn(Schedulers.boundedElastic());
//    }


    @GetMapping(path = "/api-routes/id/{id}")
    public Mono<ApiRouteResponse> getRoute(@PathVariable String id){
        return apiRoutingService.getRoute(Long.valueOf(id))
                .map(this::apiRoutesConvertToApiRouteResponse);
    }


    @GetMapping(path = "/api-routes/routes/{routeId}")
    public Mono<ApiRouteResponse> getRouteByRouteId(@PathVariable String routeId){
        return apiRoutingService.getRouteByRouteId(routeId)
                .map(this::apiRoutesConvertToApiRouteResponse);
    }

    @PutMapping(path = "/routes/{id}")
    public Mono<?> editRoute(
            @PathVariable String id,
            @RequestBody ApiRouteRequest request) {
        return apiRoutingService.updateRoute(this.apiRouteRequestConverToApiRoute(Long.valueOf(id), request))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PutMapping(path = "/routes/id/{id}")
    public Mono<ApiRouteResponse> editRouteAndGet(
            @PathVariable String id,
            @RequestBody ApiRouteRequest request){
        return apiRoutingService.updateRouteAndSelect(this.apiRouteRequestConverToApiRoute(Long.valueOf(id), request))
                .log()
                .flatMap(result -> {
                   return Mono.just(result);
                });
//  Do Something
//                .flatMap(api -> {
//                        return apiRoutingService.getRoute(Long.valueOf(id))
//                                .log()
//                                .map(this::apiRoutesConvertToApiRouteResponse);
//                    }
//                );
    }

    @PostMapping(path = "/api-routes")
    public Mono<?> addRoute(@RequestBody ApiRouteRequest request){
        return apiRoutingService.addRoute(this.apiRouteRequestConverToApiRoute(request))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping(path = "/api-routes/routes")
    public Mono<ApiRouteResponse> addRouteAndGet(@RequestBody ApiRouteRequest apiRouteRequest){
        return apiRoutingService.addRouteAndGet(this.apiRouteRequestConverToApiRoute(apiRouteRequest));
    }

    @DeleteMapping(path = "/api-routes/routes/{routeId}")
    public Mono<?> deleteRoute(@PathVariable String routeId){
        return apiRoutingService.deleteRoute(routeId);
    }

}
