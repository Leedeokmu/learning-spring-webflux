package com.freeefly.webfluxpatterns.sec05.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CarReservationRequest {
    private String city;
    private LocalDate pickup;
    private LocalDate drop;
    private String category;



}
