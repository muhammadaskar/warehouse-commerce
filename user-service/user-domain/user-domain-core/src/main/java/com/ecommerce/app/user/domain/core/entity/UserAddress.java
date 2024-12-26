package com.ecommerce.app.user.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.domain.core.valueobject.AddressId;

import java.util.UUID;

public class UserAddress extends BaseEntity<AddressId> {
    private UserId userId;
    private Address address;
    private boolean isPrimary;

    public void initializeUserAddress(UserId userId) {
        super.setId(new AddressId(UUID.randomUUID()));
        this.userId = userId;
    }

    private UserAddress(Builder builder) {
        super.setId(builder.id);
        userId = builder.userId;
        address = builder.address;
        isPrimary = builder.isPrimary;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private AddressId id;
        private UserId userId;
        private Address address;
        private boolean isPrimary;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(AddressId val) {
            id = val;
            return this;
        }

        public Builder withUserId(UserId val) {
            userId = val;
            return this;
        }

        public Builder withAddress(Address val) {
            address = val;
            return this;
        }

        public Builder withIsPrimary(boolean val) {
            isPrimary = val;
            return this;
        }

        public UserAddress build() {
            return new UserAddress(this);
        }
    }
}
