package com.se1853_jv.apigateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Configuration

@Configuration
class ApiGatewayConfig {
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("account_route") { r ->
                r.path("/v1/api/accounts/**")
                    .filters { f ->
                        f.addRequestHeader("X-Account-Service", "AccountServiceHeader")
                        f.rewritePath("/accounts/(?<segment>.*)", "/$\\{segment}")
                    }
                    .uri("http://localhost:8081")
            }
            .route("notification_route") { r ->
                r.path("/v1/api/notifications/**")
                    .filters { f ->
                        f.addRequestHeader("X-Notification-Service", "NotificationServiceHeader")
                        f.rewritePath("/notifications/(?<segment>.*)", "/$\\{segment}")
                    }
                    .uri("http://localhost:8082")
            }
            .build()
    }
}