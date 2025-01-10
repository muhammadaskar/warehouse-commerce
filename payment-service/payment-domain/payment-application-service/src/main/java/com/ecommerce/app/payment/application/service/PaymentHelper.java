package com.ecommerce.app.payment.application.service;

import com.ecommerce.app.common.application.service.dto.header.AuthorizationHeader;
import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.payment.application.service.dto.create.CreatePaymentApproveCommand;
import com.ecommerce.app.payment.application.service.dto.create.CreatePaymentProofCommand;
import com.ecommerce.app.payment.application.service.dto.create.OrderIdQuery;
import com.ecommerce.app.payment.application.service.dto.create.PaymentIdQuery;
import com.ecommerce.app.payment.application.service.dto.message.PaymentRequest;
import com.ecommerce.app.payment.application.service.mapper.PaymentDataMapper;
import com.ecommerce.app.payment.application.service.ports.output.PaymentRepository;
import com.ecommerce.app.payment.application.service.ports.output.message.publisher.payment.PaymentApprovedMessagePublisher;
import com.ecommerce.app.payment.application.service.ports.output.message.publisher.payment.PaymentProofUploadedMessagePublisher;
import com.ecommerce.app.payment.domain.core.PaymentDomainService;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import com.ecommerce.app.payment.domain.core.entity.User;
import com.ecommerce.app.payment.domain.core.event.PaymentApprovedEvent;
import com.ecommerce.app.payment.domain.core.event.PaymentProofUploadedEvent;
import com.ecommerce.app.payment.domain.core.exception.PaymentDomainException;
import com.ecommerce.app.payment.domain.core.exception.PaymentNotFoundException;
import com.ecommerce.app.payment.domain.core.exception.UserForbidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class PaymentHelper {

    private final PaymentDomainService paymentDomainService;
    private final PaymentRepository paymentRepository;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentProofUploadedMessagePublisher paymentProofUploadedMessagePublisher;
    private final PaymentApprovedMessagePublisher paymentApprovedMessagePublisher;
    private final UserHelper userHelper;

    public PaymentHelper(PaymentDomainService paymentDomainService, PaymentRepository paymentRepository, PaymentDataMapper paymentDataMapper, PaymentProofUploadedMessagePublisher paymentProofUploadedMessagePublisher, PaymentApprovedMessagePublisher paymentApprovedMessagePublisher, UserHelper userHelper) {
        this.paymentDomainService = paymentDomainService;
        this.paymentRepository = paymentRepository;
        this.paymentDataMapper = paymentDataMapper;
        this.paymentProofUploadedMessagePublisher = paymentProofUploadedMessagePublisher;
        this.paymentApprovedMessagePublisher = paymentApprovedMessagePublisher;
        this.userHelper = userHelper;
    }

    /**
     * Create payment with payment request
     *
     * @param paymentRequest PaymentRequest
     */
    @Transactional
    public void createPayment(PaymentRequest paymentRequest) {
        log.info("Creating payment with id: {}", paymentRequest.getId());
        Payment createdPayment = paymentDataMapper.paymentRequestToPayment(paymentRequest);
        paymentDomainService.createPayment(createdPayment);
        savePayment(createdPayment);
    }

    /**
     * Create payment proof with payment proof request
     *
     * @param createPaymentProofCommand CreatePaymentProofCommand
     * @param authorizationHeader AuthorizationHeader
     * @return PaymentProofUploadedEvent
     */
    @Transactional
    public PaymentProofUploadedEvent createPaymentProof(CreatePaymentProofCommand createPaymentProofCommand, AuthorizationHeader authorizationHeader) {
        log.info("Creating payment proof for order with id: {}", createPaymentProofCommand.getOrderId());
        User userLoggedIn = userHelper.getUserFromLoggedIn(authorizationHeader);
        Payment payment = paymentDataMapper.createPaymentProofToPayment(createPaymentProofCommand);
        Payment existingPayment = findPaymentByOrderId(payment.getOrderId());
        PaymentProofUploadedEvent paymentProofUploadedEvent = paymentDomainService.createPaymentProof(userLoggedIn, existingPayment,
                payment.getPaymentProof(), paymentProofUploadedMessagePublisher);
        savePayment(paymentProofUploadedEvent.getPayment());
        return paymentProofUploadedEvent;
    }


    /**
     * Find payment by payment id
     *
     * @param paymentIdQuery PaymentIdQuery
     * @param authorizationHeader AuthorizationHeader
     * @return Payment
     */
    @Transactional(readOnly = true)
    public Payment findPaymentById(PaymentIdQuery paymentIdQuery, AuthorizationHeader authorizationHeader) {
        log.info("Finding payment with id: {}", paymentIdQuery.getPaymentId().toString());
        User userLoggedIn = userHelper.getUserFromLoggedIn(authorizationHeader);
        PaymentId paymentId = new PaymentId(paymentIdQuery.getPaymentId());
        Payment payment = findPayment(paymentId);

        checkWarehouseAdmin(userLoggedIn, payment);

        if (userLoggedIn.getUserRole().equals(UserRole.CUSTOMER) && !userLoggedIn.getId().equals(payment.getOrder().getUserId())) {
            throw new UserForbidden("User forbidden");
        }
        return payment;
    }


    /**
     * Find payment by order id
     *
     * @param orderIdQuery OrderIdQuery
     * @param authorizationHeader AuthorizationHeader
     * @return Payment
     */
    @Transactional(readOnly = true)
    public Payment findPaymentByOrderId(OrderIdQuery orderIdQuery, AuthorizationHeader authorizationHeader) {
        log.info("Finding payment with order id: {}", orderIdQuery.getOrderId().toString());
        User userLoggedIn = userHelper.getUserFromLoggedIn(authorizationHeader);
        OrderId orderId = new OrderId(orderIdQuery.getOrderId());
        Payment payment = findPaymentByOrderId(orderId);

        checkWarehouseAdmin(userLoggedIn, payment);

        if (userLoggedIn.getUserRole().equals(UserRole.CUSTOMER) && !userLoggedIn.getId().equals(payment.getOrder().getUserId())) {
            throw new UserForbidden("User forbidden");
        }
        return payment;
    }

    /**
     * Approve payment with payment approve request
     *
     * @param paymentRequest CreatePaymentApproveCommand
     * @param authorizationHeader AuthorizationHeader
     * @return PaymentApprovedEvent
     */
    @Transactional
    public PaymentApprovedEvent approvePayment(CreatePaymentApproveCommand paymentRequest, AuthorizationHeader authorizationHeader) {
        log.info("Updating payment status for payment with id: {}", paymentRequest.getPaymentId().toString());
        User userLoggedIn = userHelper.getUserFromLoggedIn(authorizationHeader);
        Payment payment = paymentDataMapper.approvePaymentToPayment(paymentRequest);
        Payment existingPayment = findPayment(payment.getId());
        PaymentApprovedEvent paymentApprovedEvent = paymentDomainService.approvePayment(userLoggedIn, existingPayment, paymentApprovedMessagePublisher);
        savePayment(existingPayment);
        log.info("Payment with id: {} has been approved", paymentRequest.getPaymentId().toString());
        return paymentApprovedEvent;
    }

    /**
     * Find payment by payment id
     *
     * @param payment Payment
     */
    private void savePayment(Payment payment) {
        Payment paymentResult = paymentRepository.save(payment);
        if (paymentResult == null) {
            throw new PaymentDomainException("Payment not saved");
        }
    }

    /**
     * Find payment by payment id
     *
     * @param paymentId PaymentId
     * @return Payment
     */
    private Payment findPayment(PaymentId paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isEmpty()) {
            log.error("Payment with id: {} could not be found!", paymentId.getValue().toString());
            throw new PaymentNotFoundException("Payment with id " + paymentId.getValue().toString() + " could not be found!");
        }
        return payment.get();
    }

    /**
     * Find payment by order id
     *
     * @param orderId OrderId
     * @return Payment
     */
    private Payment findPaymentByOrderId(OrderId orderId) {
        Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
        if (payment.isEmpty()) {
            log.error("Payment with order id: {} could not be found!", orderId.getValue().toString());
            throw new PaymentNotFoundException("Payment with order id " + orderId.getValue().toString() + " could not be found!");
        }
        return payment.get();
    }

    /**
     * Check if user is warehouse admin
     *
     * @param userLoggedIn User
     * @param payment Payment
     */
    private void checkWarehouseAdmin(User userLoggedIn, Payment payment) {
        if (userLoggedIn.getUserRole().equals(UserRole.WAREHOUSE_ADMIN) && !userLoggedIn.getWarehouseId().equals(payment.getOrder().getWarehouseId())) {
            throw new UserForbidden("User forbidden");
        }
    }
}
