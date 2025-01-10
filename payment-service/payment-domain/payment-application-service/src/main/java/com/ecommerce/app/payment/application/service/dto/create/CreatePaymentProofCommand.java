package com.ecommerce.app.payment.application.service.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreatePaymentProofCommand {
    @NotNull
    private final UUID orderId;
    @NotNull
    private final String paymentProof;
}
