package com.freeefly.webfluxpatterns.sec04.service;

import static java.util.Objects.nonNull;

import com.freeefly.webfluxpatterns.sec04.client.ShippingClient;
import com.freeefly.webfluxpatterns.sec04.dto.Status;
import com.freeefly.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ShippingOrchestrator extends Orchestrator {
    private final ShippingClient client;
    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return client.schedule(context.getShippingRequest())
            .doOnNext(context::setShippingResponse)
            .thenReturn(context)
            .handle(statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> nonNull(context.getShippingResponse()) && Status.SUCCESS.equals(context.getShippingResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return context -> Mono.just(context)
            .filter(isSuccess())
            .map(OrchestrationRequestContext::getShippingRequest)
            .flatMap(client::cancel)
            .subscribe();
    }
}
