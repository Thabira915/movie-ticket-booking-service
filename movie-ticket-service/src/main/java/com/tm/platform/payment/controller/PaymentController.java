package com.tm.platform.payment.controller;

import com.tm.platform.payment.dto.PaymentRequestDTO;
import com.tm.platform.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDTO paymentRequestDTO){
        String result = paymentService.processPayment(paymentRequestDTO);
        return ResponseEntity.ok(result);
    }

}
