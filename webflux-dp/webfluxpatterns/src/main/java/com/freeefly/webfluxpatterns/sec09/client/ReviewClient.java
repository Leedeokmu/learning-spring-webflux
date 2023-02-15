package com.freeefly.webfluxpatterns.sec09.client;

import com.freeefly.webfluxpatterns.sec09.dto.Review;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${sec09.review.service}") String baseUrl) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @RateLimiter(name = "review-service", fallbackMethod = "fallback")
    public Mono<List<Review>> getReview(Integer id) {
        return client.get()
            .uri("{id}", id)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())
            .bodyToFlux(Review.class)
            .collectList();
    }

    public Mono<List<Review>> fallback(Integer id, Throwable ex) {
        return Mono.just(Collections.emptyList());
    }
}
