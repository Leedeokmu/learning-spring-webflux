package com.freeefly.webfluxpatterns.sec03.service;

import com.freeefly.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import com.freeefly.webfluxpatterns.sec03.dto.Status;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OrderFulfillmentService {
    private final List<Orchestrator> orchestrators;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext context) {
        List<Mono<OrchestrationRequestContext>> list = orchestrators.stream()
            .map(o -> o.create(context))
            .toList();
        return Mono.zip(list, a -> a[0])
            .cast(OrchestrationRequestContext.class)
            .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestrationRequestContext context) {
        boolean allSuccess = orchestrators.stream().allMatch(o -> o.isSuccess().test(context));
        context.setStatus(allSuccess ? Status.SUCCESS : Status.FAILED);
    }
}
