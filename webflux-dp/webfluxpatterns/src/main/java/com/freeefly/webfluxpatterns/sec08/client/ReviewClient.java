package com.freeefly.webfluxpatterns.sec08.client;

import com.freeefly.webfluxpatterns.sec08.dto.Review;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ReviewClient {
    private final WebClient client;

    public ReviewClient(@Value("${sec08.review.service}") String baseUrl) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @CircuitBreaker(name = "review-service", fallbackMethod = "fallbackReview")
    public Mono<List<Review>> getReview(Integer id) {
        return client.get()
            .uri("{id}", id)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())
            .bodyToFlux(Review.class)
            .collectList()
            .retry(5)
            .timeout(Duration.ofMillis(300));
    }

    public Mono<List<Review>> fallbackReview(Integer id, Throwable ex) {
        log.info("fallback reviews called: {}", ex.getMessage());
        return Mono.just(Collections.emptyList());
    }
}
