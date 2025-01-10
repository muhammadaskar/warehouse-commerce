package com.ecommerce.app.payment.dataaccess.order.adapter;

import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.payment.application.service.ports.output.OrderRepository;
import com.ecommerce.app.payment.dataaccess.order.mapper.OrderDataAccessMapper;
import com.ecommerce.app.payment.dataaccess.order.repository.OrderJpaRepository;
import com.ecommerce.app.payment.domain.core.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository, OrderDataAccessMapper orderDataAccessMapper) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
    }

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(orderJpaRepository.
                save(orderDataAccessMapper.orderToOrderEntity(order)));
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId.getValue()).map(orderDataAccessMapper::orderEntityToOrder);
    }
}
