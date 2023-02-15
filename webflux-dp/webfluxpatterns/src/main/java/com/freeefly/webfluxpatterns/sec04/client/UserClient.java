package com.freeefly.webfluxpatterns.sec04.client;

import com.freeefly.webfluxpatterns.sec04.dto.PaymentRequest;
import com.freeefly.webfluxpatterns.sec04.dto.PaymentResponse;
import com.freeefly.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {
    private final WebClient client;
    private final static String REFUND = "refund";
    private final static String DEDUCT = "deduct";

    public UserClient(@Value("${sec04.user.service}") String baseUrl) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<PaymentResponse> deduct(PaymentRequest request) {
        return callUserService(DEDUCT, request);
    }

    public Mono<PaymentResponse> refund(PaymentRequest request) {
        return callUserService(REFUND, request);
    }

    private Mono<PaymentResponse> callUserService(String endPoint, PaymentRequest request) {
        return client.post()
            .uri(endPoint)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(PaymentResponse.class)
            .onErrorReturn(buildErrorResponse(request))
            ;
    }

    private PaymentResponse buildErrorResponse(PaymentRequest request) {
        return PaymentResponse.of(
            null,
            request.getUserId(),
            null,
            request.getAmount(),
            Status.FAILED
        );
    }
}
