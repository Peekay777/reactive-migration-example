package com.koutsios.reactivemigrationexample.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import com.koutsios.reactivemigrationexample.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

  @Bean
  public RouterFunction<ServerResponse> route(ProductHandler productHandler) {
    return RouterFunctions
        .route(GET("/func/product/{id}"), productHandler::getProduct)
        .andRoute(GET("/func/product"), req -> productHandler.getAllProducts())
        .andRoute(POST("/func/product"), productHandler::createProduct);
  }
}
