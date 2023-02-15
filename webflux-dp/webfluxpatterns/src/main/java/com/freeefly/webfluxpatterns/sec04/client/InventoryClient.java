package com.freeefly.webfluxpatterns.sec04.client;

import com.freeefly.webfluxpatterns.sec04.dto.InventoryResponse;
import com.freeefly.webfluxpatterns.sec04.dto.InventoryRequest;
import com.freeefly.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class InventoryClient {
    private final WebClient client;
    private final static String DEDUCT = "deduct";
    private final static String RESTORE = "restore";

    public InventoryClient(@Value("${sec04.inventory.service}") String baseUrl) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest request) {
        return callInventoryService(DEDUCT, request);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return callInventoryService(RESTORE, request);
    }

    private Mono<InventoryResponse> callInventoryService(String endPoint, InventoryRequest request) {
        return client.post()
            .uri(endPoint)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(InventoryResponse.class)
            .onErrorReturn(buildErrorResponse(request))
            ;
    }

    private InventoryResponse buildErrorResponse(InventoryRequest request) {
        return InventoryResponse.of(
            null,
            request.getProductId(),
            request.getQuantity(),
            null,
            Status.FAILED
        );
    }
}
