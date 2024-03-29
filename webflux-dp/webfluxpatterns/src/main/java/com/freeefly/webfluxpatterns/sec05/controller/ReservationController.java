package com.freeefly.webfluxpatterns.sec05.controller;

import com.freeefly.webfluxpatterns.sec05.dto.ReservationItemRequest;
import com.freeefly.webfluxpatterns.sec05.dto.ReservationResponse;
import com.freeefly.webfluxpatterns.sec05.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("sec05")
public class ReservationController {

    private final ReservationService service;

    @PostMapping("reserve")
    public Mono<ReservationResponse> reserve(@RequestBody Flux<ReservationItemRequest> requestMono) {
        return service.reserve(requestMono);
    }
}
