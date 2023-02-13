package com.freeefly.webfluxpatterns.sec01.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Price {
    private Integer listPrice;
    private Double discount;
    private Double discountedPrice;
    private Double amountSaved;
    private LocalDate endDate;
}
