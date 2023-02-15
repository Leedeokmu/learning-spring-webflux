package com.freeefly.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;

}
