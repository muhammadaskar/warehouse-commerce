package com.ecommerce.app.payment.domain.core;

import com.ecommerce.app.payment.domain.core.entity.Order;
import com.ecommerce.app.payment.domain.core.entity.User;

public class OrderDomainServiceImpl implements OrderDomainService{

    @Override
    public void createOrder(User user, Order order) {
        user.checkIsCustomer();
        order.initializeOrder();
    }

    @Override
    public void updateOrderToPendingPayment(Order order) {
        order.updateOrderToPendingPayment();
    }

    @Override
    public void updateOrderToApprove(Order order) {
        order.updateOrderToApprove();
    }

    @Override
    public void updateOrderToProcessed(Order order) {
        order.updateOrderToProcessed();
    }

    @Override
    public void updateOrderToShipped(Order order) {
        order.updateOrderToShipped();
    }

    @Override
    public void updateOrderToConfirmed(Order order) {
        order.updateOrderToConfirmed();
    }
}
