package com.ecommerce.app.user.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.user.domain.core.exception.UserException;

import java.util.List;
import java.util.UUID;

public class User extends BaseEntity<UserId> {
    private final WarehouseId warehouseId;
    private final String username;
    private final String email;
    private final String password;
    private final boolean isEmailVerified;
    private final List<Address> address;
    private UserRole role;

    public void initializeAdmin() {
        initializeUser();
        role = UserRole.WAREHOUSE_ADMIN;
    }

    public void validateAdmin() {
        // TODO: must to be check super admin role by token
        validateUsername();
        validateAddress();
    }

    private void initializeUser(){
        setId(new UserId(UUID.randomUUID()));
    }

    private void validateUsername() {
        if (username == null) {
            throw new UserException("Username is required");
        } else if (username.length() < 5) {
           throw new UserException("Username must be at least 5 characters");
        }
    }

    private void validateAddress() {
        for (Address address : address) {
            if (address.getCity() == null || address.getStreet() == null || address.getPostalCode() == null) {
                throw new UserException("Address must contain at least one street or postal code");
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    private User(Builder builder) {
        super.setId(builder.id);
        warehouseId = builder.warehouseId;
        username = builder.username;
        email = builder.email;
        password = builder.password;
        isEmailVerified = builder.isEmailVerified;
        address = builder.address;
        role = builder.role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private WarehouseId warehouseId;
        private UserId id;
        private String username;
        private String email;
        private String password;
        private boolean isEmailVerified;
        private List<Address> address;
        private UserRole role;

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

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withPassword(String val) {
            password = val;
            return this;
        }

        public Builder withIsEmailVerified(boolean val) {
            isEmailVerified = val;
            return this;
        }

        public Builder withAddress(List<Address> val) {
            address = val;
            return this;
        }

        public Builder withRole(UserRole val) {
            role = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
