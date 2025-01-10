package com.ecommerce.app.payment.domain.core;

import com.ecommerce.app.payment.domain.core.entity.Order;
import com.ecommerce.app.payment.domain.core.entity.User;

public interface OrderDomainService {
    void createOrder(User user, Order order);
    void updateOrderToPendingPayment(Order order);
    void updateOrderToApprove(Order order);
    void updateOrderToProcessed(Order order);
    void updateOrderToShipped(Order order);
    void updateOrderToConfirmed(Order order);
}
