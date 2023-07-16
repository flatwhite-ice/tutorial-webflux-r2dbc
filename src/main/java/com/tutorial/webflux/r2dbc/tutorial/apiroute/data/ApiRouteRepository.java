package com.tutorial.webflux.r2dbc.tutorial.apiroute.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ApiRouteRepository extends ReactiveCrudRepository<ApiRoute, Long> {

    Mono<ApiRoute> findApiRouteByRouteId(String routeId);
    Mono<Void> deleteApiRouteByRouteId(String routeId);
}
