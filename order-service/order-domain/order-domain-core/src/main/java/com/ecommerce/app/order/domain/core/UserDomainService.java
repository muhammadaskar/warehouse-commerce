package com.ecommerce.app.order.domain.core;

import com.ecommerce.app.order.domain.core.entity.User;

import java.util.UUID;

public interface UserDomainService {
    void checkActiveUser(User user);
    UUID getUserId(User user);
}
