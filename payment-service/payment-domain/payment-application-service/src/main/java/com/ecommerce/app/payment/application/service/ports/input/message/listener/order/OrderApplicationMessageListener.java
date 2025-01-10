package com.ecommerce.app.payment.application.service.ports.input.message.listener.order;

import com.ecommerce.app.payment.application.service.dto.message.*;

public interface OrderApplicationMessageListener {
    void updateOrderStatusToPending(PaymentProofResponse paymentProofResponse);
    void updateOrderStatusToApproved(OrderPaidRequest orderPaidRequest);
    void updateOrderStatusToProcessed(OrderProcessedRequest orderProcessedRequest);
    void updateOrderStatusToShipped(OrderShippedRequest orderShippedRequest);
    void updateOrderStatusToConfirmed(OrderConfirmedRequest orderConfirmedRequest);
}
