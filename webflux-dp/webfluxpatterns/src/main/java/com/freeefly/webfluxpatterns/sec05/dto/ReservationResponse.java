package com.freeefly.webfluxpatterns.sec05.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ReservationResponse {
    private UUID reservationId;
    private Integer price;
    private List<ReservationItemResponse> items;
}
