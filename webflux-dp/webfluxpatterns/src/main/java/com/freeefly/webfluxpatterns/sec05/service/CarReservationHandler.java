package com.freeefly.webfluxpatterns.sec05.service;

import com.freeefly.webfluxpatterns.sec05.client.CarClient;
import com.freeefly.webfluxpatterns.sec05.dto.CarReservationRequest;
import com.freeefly.webfluxpatterns.sec05.dto.CarReservationResponse;
import com.freeefly.webfluxpatterns.sec05.dto.ReservationItemRequest;
import com.freeefly.webfluxpatterns.sec05.dto.ReservationItemResponse;
import com.freeefly.webfluxpatterns.sec05.dto.ReservationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class CarReservationHandler extends ReservationHandler {
    private final CarClient client;
    @Override
    protected ReservationType getType() {
        return ReservationType.CAR;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> requestFlux) {
        return requestFlux.map(this::toCarRequest)
            .transform(this.client::reserve)
            .map(this::toResponse)
            ;
    }

    private CarReservationRequest toCarRequest(ReservationItemRequest request) {
        return CarReservationRequest.of(
            request.getCity(),
            request.getFrom(),
            request.getTo(),
            request.getCategory()
        );
    }

    private ReservationItemResponse toResponse(CarReservationResponse response) {
        return ReservationItemResponse.of(
            response.getReservationId(),
            this.getType(),
            response.getCategory(),
            response.getCity(),
            response.getPickup(),
            response.getDrop(),
            response.getPrice()


        );
    }
}
