package com.dw.paymentservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private Date onboardedDate;
    private String email;
    private String phone;
    private Double accountBalance;
    private String paymentMethod;
    private String accountNumber;

}