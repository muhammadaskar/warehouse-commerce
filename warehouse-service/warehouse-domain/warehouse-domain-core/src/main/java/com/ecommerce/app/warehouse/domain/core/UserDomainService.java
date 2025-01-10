package com.ecommerce.app.warehouse.domain.core;

import com.ecommerce.app.warehouse.domain.core.entity.User;

public interface UserDomainService {
    void checkActiveUser(User user);
}
