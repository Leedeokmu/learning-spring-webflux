package com.freeefly.webfluxpatterns.sec10.dto;

import lombok.Data;

@Data
public class Review {

    private Integer id;
    private String user;
    private Integer rating;
    private String comment;


}
