package com.freeefly.webfluxpatterns.sec02.controller;

import com.freeefly.webfluxpatterns.sec02.dto.FlightResult;
import com.freeefly.webfluxpatterns.sec02.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("sec02")
public class FlightsController {
    private final FlightSearchService flightSearchService;

    @GetMapping(value = "flights/{from}/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> getFlights(@PathVariable String from, @PathVariable String to) {
        return flightSearchService.getFlights(from, to);
    }
}
