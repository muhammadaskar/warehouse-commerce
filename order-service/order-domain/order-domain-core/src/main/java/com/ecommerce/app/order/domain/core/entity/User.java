package com.ecommerce.app.order.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.order.domain.core.exception.OrderException;

public class User extends BaseEntity<UserId> {
    private final String email;
    private final boolean isEmailVerified;
    private final UserRole role;
    private final WarehouseId warehouseId;

    private User(Builder builder) {
        super.setId(builder.id);
        email = builder.email;
        isEmailVerified = builder.isEmailVerified;
        role = builder.role;
        warehouseId = builder.warehouseId;
    }

    public void checkActiveUser() {
        if (!isEmailVerified) {
            throw new OrderException("User is not active");
        }
        if (role == null) {
            throw new OrderException("User role is not defined");
        }
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public UserRole getRole() {
        return role;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UserId id;
        private String email;
        private boolean isEmailVerified;
        private UserRole role;
        private WarehouseId warehouseId;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(UserId val) {
            id = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withIsEmailVerified(boolean val) {
            isEmailVerified = val;
            return this;
        }

        public Builder withRole(UserRole val) {
            role = val;
            return this;
        }

        public Builder withWarehouseId(WarehouseId val) {
            warehouseId = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
