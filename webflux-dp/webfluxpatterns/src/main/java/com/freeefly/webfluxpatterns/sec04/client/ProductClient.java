package com.freeefly.webfluxpatterns.sec04.client;

import com.freeefly.webfluxpatterns.sec04.dto.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {
    private final WebClient client;

    public ProductClient(@Value("${sec04.product.service}") String baseUrl) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<Product> getProduct(Integer id) {
        return client.get()
            .uri("{id}", id)
            .retrieve()
            .bodyToMono(Product.class)
            .onErrorResume(ex -> Mono.empty())
            ;
    }
}
