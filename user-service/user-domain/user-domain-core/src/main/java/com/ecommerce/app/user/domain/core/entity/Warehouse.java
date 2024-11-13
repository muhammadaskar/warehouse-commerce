package com.ecommerce.app.user.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;

public class Warehouse extends BaseEntity<WarehouseId> {

    private Warehouse(Builder builder) {
        super.setId(builder.id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private WarehouseId id;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(WarehouseId val) {
            id = val;
            return this;
        }

        public Warehouse build() {
            return new Warehouse(this);
        }
    }
}
