package com.dw.paymentservice.service;

import com.dw.paymentservice.entity.Payment;
import com.dw.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment getOrderById(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
