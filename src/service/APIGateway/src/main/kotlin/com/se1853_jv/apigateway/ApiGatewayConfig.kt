package com.se1853_jv.apigateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiGatewayConfig {
//    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("book-service") { r ->
                r.path("/v1/api/books/**")
                    .filters { f ->
                        f.addRequestHeader("X-Book-Service", "BookServiceHeader")
                        f.rewritePath("/v1/api/books/(?<segment>.*)", "/$\\{segment}")
                    }
                    .uri("http://localhost:8081")
            }
            .route("book-service-docs") { r ->
                r.path("/v1/api/v3/api-docs")
                    .filters { f ->
                        f.addRequestHeader("X-Book-Service", "BookServiceHeader")
                        f.rewritePath("/v1/api/(?<segment>.*)", "/$\\{segment}")
                    }
                    .uri("http://localhost:8081")
            }
            .build()
    }
}