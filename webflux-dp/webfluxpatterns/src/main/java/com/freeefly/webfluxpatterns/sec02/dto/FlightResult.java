package com.freeefly.webfluxpatterns.sec02.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class FlightResult {
    private String airline;
    private String from;
    private String to;
    private Double price;
    private LocalDate date;
}
