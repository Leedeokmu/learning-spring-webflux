package com.freeefly.webfluxpatterns.sec01.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionResponse {
    private Integer id;
    private String type;
    private Double discount;
    private LocalDate endDate;
}
