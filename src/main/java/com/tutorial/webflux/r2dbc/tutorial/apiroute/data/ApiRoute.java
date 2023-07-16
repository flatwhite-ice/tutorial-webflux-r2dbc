package com.tutorial.webflux.r2dbc.tutorial.apiroute.data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("api_route")
public class ApiRoute {

    @Id
    private Long id;
    private String path;
    private String method;
    private String uri;
    private String weightGroup;
    private Long weights;
    private String routeId;

}
