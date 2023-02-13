package com.freeefly.webfluxpatterns.sec01.service;

import com.freeefly.webfluxpatterns.sec01.client.ProductClient;
import com.freeefly.webfluxpatterns.sec01.client.PromotionClient;
import com.freeefly.webfluxpatterns.sec01.client.ReviewClient;
import com.freeefly.webfluxpatterns.sec01.dto.Price;
import com.freeefly.webfluxpatterns.sec01.dto.ProductAggregate;
import com.freeefly.webfluxpatterns.sec01.dto.ProductResponse;
import com.freeefly.webfluxpatterns.sec01.dto.PromotionResponse;
import com.freeefly.webfluxpatterns.sec01.dto.Review;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class ProductAggregatorService {
    private final ProductClient productClient;
    private final PromotionClient promotionClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id) {
        return Mono.zip(
                productClient.getProduct(id),
                promotionClient.getPromotion(id),
                reviewClient.getReview(id)
            )
            .map(t -> toDto(t.getT1(), t.getT2(), t.getT3()))
        ;
    }

    private ProductAggregate toDto(ProductResponse product, PromotionResponse promotion, List<Review> reviews) {
        Price price = new Price();
        double discountAmount = product.getPrice() * promotion.getDiscount() / 100;
        double discountedPrice = product.getPrice() - discountAmount;
        price.setListPrice(product.getPrice());
        price.setAmountSaved(discountAmount);
        price.setDiscountedPrice(discountedPrice);
        price.setDiscount(promotion.getDiscount());
        price.setEndDate(promotion.getEndDate());
        return ProductAggregate.builder()
            .id(product.getId())
            .category(product.getCategory())
            .description(product.getDescription())
            .reviews(reviews)
            .price(price)
            .build();
    }



}
