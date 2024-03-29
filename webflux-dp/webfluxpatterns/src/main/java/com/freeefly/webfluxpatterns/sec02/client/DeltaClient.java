package com.freeefly.webfluxpatterns.sec02.client;

import com.freeefly.webfluxpatterns.sec02.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DeltaClient {

    private final WebClient client;

    public DeltaClient(@Value("${sec02.delta.service}") String baseUrl) {
        this.client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Flux<FlightResult> getFlight(String from, String to) {
        return this.client
            .get()
            .uri("{from}/{to}", from, to)
            .retrieve()
            .bodyToFlux(FlightResult.class)
            .onErrorResume(ex -> Mono.empty())
            ;
    }
}
