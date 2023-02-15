package com.freeefly.webfluxpatterns.sec05.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class RoomReservationRequest {
    private String city;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String category;



}
