package com.freeefly.webfluxpatterns.sec01.client;

import com.freeefly.webfluxpatterns.sec01.dto.PromotionResponse;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PromotionClient {
    private final WebClient client;
    private final static PromotionResponse NO_PROMOTION = PromotionResponse.builder()
        .id(-1)
        .type("no promotion")
        .discount(0.0)
        .endDate(LocalDate.now())
        .build();

    public PromotionClient(@Value("${sec01.promotion.service}") String baseUrl) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<PromotionResponse> getPromotion(Integer id) {
        return client.get()
            .uri("{id}", id)
            .retrieve()
            .bodyToMono(PromotionResponse.class)
            .onErrorReturn(NO_PROMOTION)
            .log()
            ;
    }
}
