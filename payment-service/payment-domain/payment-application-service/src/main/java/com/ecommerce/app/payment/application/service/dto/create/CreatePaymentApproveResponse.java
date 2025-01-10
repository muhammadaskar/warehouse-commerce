package com.ecommerce.app.payment.application.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreatePaymentApproveResponse {
    private final UUID paymentId;
    private final String message;
}
