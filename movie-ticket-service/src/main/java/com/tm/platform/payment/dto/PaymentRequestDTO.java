package com.tm.platform.payment.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long bookingId;
    private Double amount;
    private String transactionId;
}
