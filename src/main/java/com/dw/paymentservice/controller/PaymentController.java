package com.dw.paymentservice.controller;

import com.dw.paymentservice.entity.Payment;
import com.dw.paymentservice.service.PaymentService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{orderId}")
    public Payment getPaymentInfo(@PathVariable String orderId) {
        return paymentService.getOrderById(orderId);
    }
}
