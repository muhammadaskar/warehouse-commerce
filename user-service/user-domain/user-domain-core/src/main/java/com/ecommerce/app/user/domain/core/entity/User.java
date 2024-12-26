package com.ecommerce.app.user.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.user.domain.core.exception.UserException;
import com.ecommerce.app.user.domain.core.valueobject.AddressId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends BaseEntity<UserId> {
    private WarehouseId warehouseId;
    private final String username;
    private final String email;
    private String password;
    private List<UserAddress> userAddress;
    private boolean isEmailVerified;
    private UserRole role;
    private String token;

    public void initializeCustomer() {
        initializeUser();
        role = UserRole.CUSTOMER;
    }

    public void validateCustomer() {
        validateUsername();
    }

    public void initializeAdmin() {
        initializeUser();
        role = UserRole.WAREHOUSE_ADMIN;
    }

    public void validateAdmin() {
        // TODO: must to be check super admin role by token
        validateUsername();
    }

    public void initializeAddress() {
//        if (userAddress == null) {
//            userAddress = new ArrayList<>();
//        }
//        for (UserAddress address : userAddress) {
//            System.out.println("MASUK SINI");
//            address.initializeUserAddress(super.getId());
//        }
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

    public void validateLogin() {
        if (!this.isEmailVerified) {
            throw new UserException("Email is not verified");
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void verifyEmail() {
        if (this.isEmailVerified) {
            throw new UserException("Email already verified");
        }
        isEmailVerified = true;
    }

    public void validatePassword(String password, String confirmPassword) {
        checkCreatePasswordAfterVerifyEmail();
        if (!this.isEmailVerified) {
            throw new UserException("Email is not verified");
        }

        if (password == null) {
            throw new UserException("Password is required");
        } else if (password.length() < 8) {
            throw new UserException("Password must be at least 8 characters");
        } else if (!password.equals(confirmPassword)) {
            throw new UserException("Passwords do not match");
        }
    }

    private void checkCreatePasswordAfterVerifyEmail() {
        if (this.isEmailVerified) {
            if (this.password != null) {
                throw new UserException("Password already created");
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private User(Builder builder) {
        this.token = builder.token;
        super.setId(builder.id);
        warehouseId = builder.warehouseId;
        username = builder.username;
        email = builder.email;
        password = builder.password;
        isEmailVerified = builder.isEmailVerified;
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
        private UserRole role;
        private String token;

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

        public Builder withRole(UserRole val) {
            role = val;
            return this;
        }

        public Builder withToken(String val) {
            token = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
