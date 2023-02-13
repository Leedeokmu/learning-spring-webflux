package com.freeefly.webfluxpatterns.sec01.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PromotionResponse {
    private Integer id;
    private String type;
    private Double discount;
    private LocalDate endDate;
}
