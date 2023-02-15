package com.freeefly.webfluxpatterns.sec04.service;

import static java.util.Objects.nonNull;

import com.freeefly.webfluxpatterns.sec04.client.UserClient;
import com.freeefly.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.freeefly.webfluxpatterns.sec04.dto.Status;
import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PaymentOrchestrator extends Orchestrator {
    private final UserClient client;
    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return client.deduct(context.getPaymentRequest())
            .doOnNext(context::setPaymentResponse)
            .thenReturn(context)
            .handle(statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> nonNull(context.getPaymentResponse()) && Status.SUCCESS.equals(context.getPaymentResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return context -> Mono.just(context)
            .filter(isSuccess())
            .map(OrchestrationRequestContext::getPaymentRequest)
            .flatMap(client::refund)
            .subscribe();
    }
}
