package com.freeefly.webfluxpatterns.sec09.service;

import com.freeefly.webfluxpatterns.sec09.client.ProductClient;
import com.freeefly.webfluxpatterns.sec09.client.ReviewClient;
import com.freeefly.webfluxpatterns.sec09.dto.Product;
import com.freeefly.webfluxpatterns.sec09.dto.ProductAggregate;
import com.freeefly.webfluxpatterns.sec09.dto.Review;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class ProductAggregatorService {

    private final ProductClient productClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id) {
        return Mono.zip(
                productClient.getProduct(id),
                reviewClient.getReview(id)
            )
            .map(t -> toDto(t.getT1(), t.getT2()));
    }

    private ProductAggregate toDto(Product product, List<Review> reviews) {
        return ProductAggregate.builder()
            .id(product.getId())
            .category(product.getCategory())
            .description(product.getDescription())
            .reviews(reviews)
            .build();
    }


}
