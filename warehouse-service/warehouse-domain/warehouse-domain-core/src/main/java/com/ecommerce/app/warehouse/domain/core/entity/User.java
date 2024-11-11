package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;

public class User extends BaseEntity<UserId> {
    private final UserRole userRole;
    private final Warehouse warehouses;

    public User(UserRole userRole, Warehouse warehouses) {
        this.userRole = userRole;
        this.warehouses = warehouses;
    }

    // TODO: validation role
}
