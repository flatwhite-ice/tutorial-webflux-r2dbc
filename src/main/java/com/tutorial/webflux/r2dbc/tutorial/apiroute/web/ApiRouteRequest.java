package com.tutorial.webflux.r2dbc.tutorial.apiroute.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiRouteRequest {
    private Long id;
    private String path;
    private String method;
    private String uri;
    private String weightGroup;
    private Long weights;
    private String routeId;
}

