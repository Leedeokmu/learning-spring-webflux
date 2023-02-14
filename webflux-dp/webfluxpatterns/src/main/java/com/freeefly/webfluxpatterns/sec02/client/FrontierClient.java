package com.freeefly.webfluxpatterns.sec02.client;

import com.freeefly.webfluxpatterns.sec02.dto.FlightResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FrontierClient {
    private final WebClient client;
    private final static String JETBLUE = "JETBLUE";

    public FrontierClient(@Value("${sec02.frontier.service}") String baseUrl) {
        this.client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Flux<FlightResult> getFlight(String from, String to) {
        return this.client
            .post()
            .bodyValue(FrontierRequest.of(from , to))
            .retrieve()
            .bodyToFlux(FlightResult.class)
            .doOnNext(fr -> this.normalizeResponse(fr, from, to))
            .onErrorResume(ex -> Mono.empty())
            ;
    }

    private void normalizeResponse(FlightResult result, String from, String to) {
        result.setFrom(from);
        result.setTo(to);
        result.setAirline(JETBLUE);
    }

    @Builder
    @AllArgsConstructor(staticName = "of")
    @Data
    private static class FrontierRequest {
        private String from;
        private String to;
    }
}
