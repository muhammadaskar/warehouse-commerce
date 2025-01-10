package com.ecommerce.app.payment.dataaccess.payment.adapter;

import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.common.domain.valueobject.PaymentId;
import com.ecommerce.app.payment.application.service.ports.output.PaymentRepository;
import com.ecommerce.app.payment.dataaccess.payment.mapper.PaymentDataAccessMapper;
import com.ecommerce.app.payment.dataaccess.payment.repository.PaymentJpaRepository;
import com.ecommerce.app.payment.domain.core.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;

    public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository, PaymentDataAccessMapper paymentDataAccessMapper) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.paymentDataAccessMapper = paymentDataAccessMapper;
    }

    @Override
    public Payment save(Payment payment) {
        return paymentDataAccessMapper.paymentEntityToPayment(paymentJpaRepository.
                save(paymentDataAccessMapper.paymentToPaymentEntity(payment)));
    }

    @Override
    public Optional<Payment> findById(PaymentId paymentId) {
        return paymentJpaRepository.findById(paymentId.getValue()).map(paymentDataAccessMapper::paymentEntityToPayment);
    }

    @Override
    public Optional<Payment> findByOrderId(OrderId orderId) {
        return paymentJpaRepository.findByOrderId(orderId.getValue()).map(paymentDataAccessMapper::paymentEntityToPayment);
    }
}
