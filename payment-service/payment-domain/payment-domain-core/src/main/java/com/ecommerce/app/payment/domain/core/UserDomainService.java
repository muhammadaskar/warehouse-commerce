package com.ecommerce.app.payment.domain.core;

import com.ecommerce.app.payment.domain.core.entity.User;

public interface UserDomainService {
    void checkActiveUser(User user);
}
