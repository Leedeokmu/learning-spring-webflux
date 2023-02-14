package com.freeefly.webfluxpatterns.sec03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderRequest {
    private Integer userId;
    private Integer productId;
    private Integer quantity;

}
