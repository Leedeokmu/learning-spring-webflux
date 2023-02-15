package com.freeefly.webfluxpatterns.sec05.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ReservationItemResponse {
    private UUID itemId;
    private ReservationType type;
    private String category;
    private String city;
    private LocalDate from;
    private LocalDate to;
    private Integer price;
}
