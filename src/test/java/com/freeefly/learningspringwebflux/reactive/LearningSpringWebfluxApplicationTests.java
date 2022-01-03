package com.freeefly.learningspringwebflux.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
class LearningSpringWebfluxApplicationTests {

    @Test
    void contextLoads() {
        Flux<String> stringFlux = Flux.just("test1", "test2")
                .doOnNext(str -> System.out.println("onNext: " + str))
                .doOnError(System.out::println)
                .doOnComplete(() -> System.out.println("Completed"));

        stringFlux.subscribe(
                str -> System.out.println("Consuming: " + str),
                error -> System.out.println(error),
                () -> System.out.println("Completed Subscription")
        );

    }

}
