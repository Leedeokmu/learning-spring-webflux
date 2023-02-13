package com.freeefly.webfluxpatterns.sec01.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductAggregate {
    private Integer id;
    private String category;
    private String description;
    private Price price;
    private List<Review> reviews;
}
