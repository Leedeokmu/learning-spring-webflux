package com.freeefly.webfluxpatterns.sec05.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ReservationItemRequest {
    private ReservationType type;
    private String category;
    private String city;
    private LocalDate from;
    private LocalDate to;
}
