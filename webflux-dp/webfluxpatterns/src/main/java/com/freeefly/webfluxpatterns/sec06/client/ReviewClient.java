package com.freeefly.webfluxpatterns.sec06.client;

import com.freeefly.webfluxpatterns.sec06.dto.Review;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ReviewClient {
    private final WebClient client;

    public ReviewClient(@Value("${sec06.review.service}") String baseUrl) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<List<Review>> getReview(Integer id) {
        return client.get()
            .uri("{id}", id)
            .retrieve()
            .bodyToFlux(Review.class)
            .collectList()
            .timeout(Duration.ofMillis(500))
            .onErrorReturn(Collections.EMPTY_LIST)
            ;
    }
}
