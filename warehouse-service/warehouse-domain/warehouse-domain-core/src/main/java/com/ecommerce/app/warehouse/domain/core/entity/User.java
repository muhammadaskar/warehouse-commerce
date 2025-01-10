package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.exception.UserForbidden;

public class User extends BaseEntity<UserId> {
    private final WarehouseId warehouseId;
    private final boolean isEmailVerified;
    private final UserRole userRole;

    public void checkActiveUser() {
        if (!isEmailVerified) {
            throw new UserForbidden("User is not active");
        }
        if (userRole == null) {
            throw new UserForbidden("User role is not defined");
        }
    }

    private User(Builder builder) {
        super.setId(builder.id);
        warehouseId = builder.warehouseId;
        isEmailVerified = builder.isEmailVerified;
        userRole = builder.userRole;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private UserId id;
        private WarehouseId warehouseId;
        private boolean isEmailVerified;
        private UserRole userRole;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(UserId val) {
            id = val;
            return this;
        }

        public Builder withWarehouseId(WarehouseId val) {
            warehouseId = val;
            return this;
        }

        public Builder withIsEmailVerified(boolean val) {
            isEmailVerified = val;
            return this;
        }

        public Builder withUserRole(UserRole val) {
            userRole = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
