package com.freeefly.webfluxpatterns.sec08.controller;

import com.freeefly.webfluxpatterns.sec08.dto.ProductAggregate;
import com.freeefly.webfluxpatterns.sec08.service.ProductAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("sec08")
public class ProductAggregateController {

    private final ProductAggregatorService service;

    @GetMapping("product/{id}")
    public Mono<ResponseEntity<ProductAggregate>> getProductAggregate(@PathVariable Integer id) {
        return service.aggregate(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build())
            ;
    }
}
