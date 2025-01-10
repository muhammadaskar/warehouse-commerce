package com.ecommerce.app.order.dataaccess.order.adapter;

import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.order.application.service.ports.output.OrderRepository;
import com.ecommerce.app.order.dataaccess.order.entity.OrderEntity;
import com.ecommerce.app.order.dataaccess.order.mapper.OrderDataAccessMapper;
import com.ecommerce.app.order.dataaccess.order.repository.OrderJpaRepository;
import com.ecommerce.app.order.dataaccess.product.entity.ProductEntity;
import com.ecommerce.app.order.dataaccess.product.repository.ProductJpaRepository;
import com.ecommerce.app.order.domain.core.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;
    private final ProductJpaRepository productJpaRepository;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository, OrderDataAccessMapper orderDataAccessMapper, ProductJpaRepository productJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Order save(Order order) {

        List<UUID> productIds = order.getItems().stream()
                .map(item -> item.getProductId().getValue())
                .collect(Collectors.toList());

        List<ProductEntity> productEntities = productJpaRepository.findAllById(productIds);
        Map<UUID, ProductEntity> productEntityMap = productEntities.stream()
                .collect(Collectors.toMap(ProductEntity::getId, product -> product));

        OrderEntity orderEntity = orderDataAccessMapper.orderToOrderEntity(order, productEntityMap);
        OrderEntity savedOrderEntity = orderJpaRepository.save(orderEntity);
        return orderDataAccessMapper.orderEntityToOrder(savedOrderEntity);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }

    @Override
    public List<Order> findAllByUserId(UserId userId) {
        return orderJpaRepository.findAllByUserId(userId.getValue())
                .stream()
                .map(orderDataAccessMapper::orderEntityToOrder)
                .toList();
    }

    @Override
    public List<Order> findAllByWarehouseId(WarehouseId warehouseId) {
        return orderJpaRepository.findAllByWarehouseId(warehouseId.getValue())
                .stream()
                .map(orderDataAccessMapper::orderEntityToOrder)
                .toList();
    }
}
