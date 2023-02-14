package com.freeefly.webfluxpatterns.sec03.service;

import com.freeefly.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class OrderCancellationService {
    private final List<Orchestrator> orchestrators;
    private Sinks.Many<OrchestrationRequestContext> sink;
    private Flux<OrchestrationRequestContext> flux;

    @PostConstruct
    public void init() {
        sink = Sinks.many().multicast().onBackpressureBuffer();
        flux = sink.asFlux().publishOn(Schedulers.boundedElastic());
        orchestrators.forEach(o -> flux.subscribe(o.cancel()));

    }

    public void cancelOrder(OrchestrationRequestContext context) {
        sink.tryEmitNext(context);
    }
}
