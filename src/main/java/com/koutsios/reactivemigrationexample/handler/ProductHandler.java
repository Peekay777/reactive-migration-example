package com.koutsios.reactivemigrationexample.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import com.koutsios.reactivemigrationexample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

  @Autowired
  private ProductService productService;

  public Mono<ServerResponse> getProduct(ServerRequest req) {
    return ok()
        .contentType(APPLICATION_JSON)
        .body(productService.getProduct(req.pathVariable("id")), Product.class);
  }

  public Mono<ServerResponse> getAllProducts() {
    return ok()
        .contentType(APPLICATION_JSON)
        .body(productService.getAllProducts(), Product.class);
  }

  public Mono<ServerResponse> createProduct(ServerRequest req) {
    ProductDto productDto = req.body(BodyExtractors.toMono(ProductDto.class)).block();
    return ok()
        .contentType(APPLICATION_JSON)
        .body(productService.createProduct(productDto), Product.class);
  }
}
