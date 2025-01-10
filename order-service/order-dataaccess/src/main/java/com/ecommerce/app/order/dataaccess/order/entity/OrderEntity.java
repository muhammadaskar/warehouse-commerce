package com.ecommerce.app.order.dataaccess.order.entity;

import com.ecommerce.app.common.domain.valueobject.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity {
    @Id
    private UUID id;
    private UUID userId;
    private UUID cartId;
    private UUID warehouseId;
    private String shippingMethod;
    private BigDecimal shippingCost;
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderAddressEntity shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
