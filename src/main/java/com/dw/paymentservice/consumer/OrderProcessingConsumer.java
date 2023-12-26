package com.dw.paymentservice.consumer;

import com.dw.paymentservice.dto.Order;
import com.dw.paymentservice.dto.User;
import com.dw.paymentservice.entity.Payment;
import com.dw.paymentservice.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class OrderProcessingConsumer {

    @Value("${user.service.url}")
    private String USER_SERVICE_URL;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PaymentRepository paymentRepository;

    @KafkaListener(topics = "ORDER_PAYMENT_TOPIC")
    public void processOrder(String orderJsonString) {

        try {
            Order order = new ObjectMapper().readValue(orderJsonString, Order.class);
            Payment payment = Payment.builder()
                    .paymentMode(order.getPaymentMode())
                    .amount(order.getPrice())
                    .orderId(order.getOrderId())
                    .paymentDate(new Date())
                    .userId(order.getUserId())
                    .build();
            if (payment.getPaymentMode().equals("COD")) {
                payment.setPaymentStatus("PENDING");
            } else {
                System.out.println("Payment : "+payment.toString());
                ResponseEntity<User> response = restTemplate.getForEntity(USER_SERVICE_URL + "/" + payment.getUserId(), User.class);
                User user = null;
                if (response.getStatusCode() == HttpStatus.OK) {
                     user = response.getBody();
                } else {
                    System.err.println("Error: " + response.getStatusCode());
                }
                if (payment.getAmount() > user.getAccountBalance()) {
                    throw new RuntimeException("Insufficient balance to place the order.");
                } else {
                    payment.setPaymentStatus("PAID");

                    restTemplate.put(USER_SERVICE_URL + "/debit/" + payment.getUserId() + "/" + payment.getAmount(), User.class);
                }
            }
            paymentRepository.save(payment);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
