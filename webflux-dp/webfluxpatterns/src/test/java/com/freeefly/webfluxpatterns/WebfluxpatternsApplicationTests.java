package com.freeefly.webfluxpatterns;

import com.freeefly.webfluxpatterns.sec10.dto.ProductAggregate;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class WebfluxpatternsApplicationTests {

    private static WebClient webClient;

    @BeforeAll
    static void setClient() {
        webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/sec10/")
            .build();
    }

    @Test
    void concurrentTest() {
        StepVerifier.create(Flux.merge(this.fibRequest(), this.productRequests()))
            .verifyComplete();
    }

    private Mono<Void> fibRequest() {
        return Flux.range(1, 40)
            .flatMap(i -> webClient.get()
                .uri("fib/46")
                .retrieve()
                .bodyToMono(Long.class)
            ).doOnNext(this::print)
            .then();
    }

    private Mono<Void> productRequests() {
        return Mono.delay(Duration.ofMillis(100))
            .thenMany(Flux.range(1, 40))
            .flatMap(i -> webClient.get()
                .uri("product/1")
                .retrieve()
                .bodyToMono(ProductAggregate.class)
            )
            .map(ProductAggregate::getCategory)
            .doOnNext(this::print)
            .then();
    }

    private void print(Object o) {
        System.out.println(LocalDateTime.now() + " : " + o);
    }
}
