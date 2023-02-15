package com.freeefly.webfluxpatterns.sec04.dto;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PaymentResponse {
    private UUID paymentID;
    private Integer userId;
    private String name;
    private Integer balance;
    private Status status;
}
