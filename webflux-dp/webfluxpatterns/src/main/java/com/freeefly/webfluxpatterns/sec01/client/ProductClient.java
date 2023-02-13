package com.freeefly.webfluxpatterns.sec01.client;

import com.freeefly.webfluxpatterns.sec01.dto.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {
    private final WebClient client;

    public ProductClient(@Value("${sec01.product.service}") String baseUrl) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<ProductResponse> getProduct(Integer id) {
        return client.get()
            .uri("{id}", id)
            .retrieve()
            .bodyToMono(ProductResponse.class)
            .onErrorResume(ex -> Mono.empty())
            ;
    }
}
