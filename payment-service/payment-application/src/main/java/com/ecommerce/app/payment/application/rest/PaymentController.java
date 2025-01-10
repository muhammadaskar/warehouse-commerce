package com.ecommerce.app.payment.application.rest;


import com.ecommerce.app.common.application.security.annotation.RequiresRole;
import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.payment.application.service.dto.create.*;
import com.ecommerce.app.payment.application.service.ports.input.service.PaymentApplicationService;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/payments", produces = "application/vnd.api.v1+json")
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;

    public PaymentController(PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }

    @RequiresRole({UserRole.CUSTOMER})
    @PostMapping(value = "/upload-proof")
    public ResponseEntity<CreatePaymentProofResponse> createPaymentProof(@RequestBody CreatePaymentProofCommand createPaymentProofCommand, @RequestHeader("Authorization") AuthorizationHeader authorizationHeader) {
        CreatePaymentProofResponse createPaymentProofResponse = paymentApplicationService.createPaymentProof(createPaymentProofCommand, authorizationHeader);
        return ResponseEntity.ok(createPaymentProofResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN, UserRole.CUSTOMER})
    @GetMapping(value = "/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable("paymentId") UUID paymentId, @RequestHeader("Authorization") AuthorizationHeader authorizationHeader) {
        PaymentResponse paymentResponse = paymentApplicationService.getPaymentById(new PaymentIdQuery(paymentId), authorizationHeader);
        return ResponseEntity.ok(paymentResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN, UserRole.CUSTOMER})
    @GetMapping(value = "/by-order-id/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable("orderId") UUID orderId, @RequestHeader("Authorization") AuthorizationHeader authorizationHeader) {
        PaymentResponse paymentResponse = paymentApplicationService.getPaymentByOrderId(new OrderIdQuery(orderId), authorizationHeader);
        return ResponseEntity.ok(paymentResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @PostMapping(value = "/approve")
    public ResponseEntity<CreatePaymentApproveResponse> approvePayment(@RequestBody CreatePaymentApproveCommand createPaymentApproveCommand, @RequestHeader("Authorization")AuthorizationHeader authorizationHeader) {
        CreatePaymentApproveResponse createPaymentApproveResponse = paymentApplicationService.approvePayment(createPaymentApproveCommand, authorizationHeader);
        return ResponseEntity.ok(createPaymentApproveResponse);
    }
}
