package com.ecommerce.app.product.domain.core;

import com.ecommerce.app.product.domain.core.entity.User;

public interface UserDomainService {
    void checkActiveUser(User user);
}
