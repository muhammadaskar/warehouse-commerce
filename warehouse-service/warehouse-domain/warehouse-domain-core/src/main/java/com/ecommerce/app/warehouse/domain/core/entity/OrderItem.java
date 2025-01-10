package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.warehouse.domain.core.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private final ProductId productId;
    private final int quantity;

    private OrderItem(Builder builder) {
        super.setId(builder.id);
        productId = builder.productId;
        quantity = builder.quantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderItemId id;
        private ProductId productId;
        private int quantity;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(OrderItemId val) {
            id = val;
            return this;
        }

        public Builder withProductId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder withQuantity(int val) {
            quantity = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
