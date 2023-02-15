package com.freeefly.webfluxpatterns.sec05.service;

import com.freeefly.webfluxpatterns.sec05.dto.ReservationItemRequest;
import com.freeefly.webfluxpatterns.sec05.dto.ReservationItemResponse;
import com.freeefly.webfluxpatterns.sec05.dto.ReservationResponse;
import com.freeefly.webfluxpatterns.sec05.dto.ReservationType;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;


@Service
public class ReservationService {
    private final Map<ReservationType, ReservationHandler> map;
    public ReservationService(List<ReservationHandler> list) {
        map = list.stream().collect(Collectors.toMap(h -> h.getType(), Function.identity()));
    }

    public Mono<ReservationResponse> reserve(Flux<ReservationItemRequest> requestFlux) {
        return requestFlux.groupBy(r -> r.getType())
            .flatMap(this::aggregator)
            .collectList()
            .map(this::toResponse);
    }

    private Flux<ReservationItemResponse> aggregator(
        GroupedFlux<ReservationType, ReservationItemRequest> groupedFlux) {
        ReservationType key = groupedFlux.key();
        return map.get(key).reserve(groupedFlux);
    }

    private ReservationResponse toResponse(List<ReservationItemResponse> list) {
        return ReservationResponse.of(
            UUID.randomUUID(),
            list.stream().mapToInt(ReservationItemResponse::getPrice).sum(),
            list
        );
    }

}
