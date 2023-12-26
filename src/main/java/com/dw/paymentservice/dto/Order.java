package com.dw.paymentservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor
@Data
public class Order {

    private Long id;
    private String name;
    private String category;
    private Double price;
    private Date purchaseDate;
    private String orderId;
    private Long userId;
    private String paymentMode;
}