package com.ecommerce.app.product.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;

public class User extends BaseEntity<UserId> {
    private final String email;
    private final boolean isEmailVerified;
    private final UserRole role;

    public User(String email, boolean isEmailVerified, UserRole role) {
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.role = role;
    }

    public void checkActiveUser() {
        if (!isEmailVerified) {
            throw new IllegalStateException("User is not verified");
        }
        if (role == null) {
            throw new IllegalStateException("User role is not defined");
        }
    }

    private User(Builder builder) {
        super.setId(builder.id);
        email = builder.email;
        isEmailVerified = builder.isEmailVerified;
        role = builder.role;
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UserId id;
        private String email;
        private boolean isEmailVerified;
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

        public User build() {
            return new User(this);
        }
    }
}
