package com.freeefly.webfluxpatterns.sec02.service;

import com.freeefly.webfluxpatterns.sec02.client.DeltaClient;
import com.freeefly.webfluxpatterns.sec02.client.FrontierClient;
import com.freeefly.webfluxpatterns.sec02.client.JetBlueClient;
import com.freeefly.webfluxpatterns.sec02.dto.FlightResult;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class FlightSearchService {

    private final DeltaClient deltaClient;
    private final JetBlueClient jetBlueClient;
    private final FrontierClient frontierClient;

    public Flux<FlightResult> getFlights(String from, String to) {
        return Flux.merge(
            deltaClient.getFlight(from, to),
            jetBlueClient.getFlight(from, to),
            frontierClient.getFlight(from, to)
        ).take(Duration.ofSeconds(3));
    }

}
