package com.freeefly.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
public class Product {
    private Integer id;
    private String category;
    private String description;
    private Integer price;


}
