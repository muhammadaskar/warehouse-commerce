package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.AggregateRoot;
import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;

import java.util.Set;
import java.util.UUID;

public class Warehouse extends AggregateRoot<WarehouseId> {
    private final String name;
    private final Address address;
    private final Set<Product> products;

    private Warehouse(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        address = builder.address;
        products = builder.products;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeWarehouse() {
        setId(new WarehouseId(UUID.randomUUID()));
    }

    public void validateWarehouse() {
        validateName();
        validateAddress();
    }

    private void validateName() {
        if (name == null) {
            throw new WarehouseDomainException("Warehouse name is must be filled");
        }
    }

    private void validateAddress() {
        if (address.getCity().isEmpty() || address.getStreet().isEmpty() || address.getPostalCode().isEmpty()) {
            throw new WarehouseDomainException("Warehouse address must be filled");
        }
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public static final class Builder {
        private WarehouseId id;
        private String name;
        private Address address;
        private Set<Product> products;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(WarehouseId val) {
            id = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withAddress(Address val) {
            address = val;
            return this;
        }

        public Builder withProducts(Set<Product> val) {
            products = val;
            return this;
        }

        public Warehouse build() {
            return new Warehouse(this);
        }
    }
}
