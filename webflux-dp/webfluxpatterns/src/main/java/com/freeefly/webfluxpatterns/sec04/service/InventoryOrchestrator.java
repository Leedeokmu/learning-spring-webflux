package com.freeefly.webfluxpatterns.sec04.service;

import static java.util.Objects.nonNull;

import com.freeefly.webfluxpatterns.sec04.client.InventoryClient;
import com.freeefly.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.freeefly.webfluxpatterns.sec04.dto.Status;
import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class InventoryOrchestrator extends Orchestrator {
    private final InventoryClient client;
    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return client.deduct(context.getInventoryRequest())
            .doOnNext(context::setInventoryResponse)
            .thenReturn(context)
            .handle(statusHandler())
            ;
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> nonNull(context.getInventoryResponse()) && Status.SUCCESS.equals(context.getInventoryResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return context -> Mono.just(context)
            .filter(isSuccess())
            .map(OrchestrationRequestContext::getInventoryRequest)
            .flatMap(client::restore)
            .subscribe();
    }
}
