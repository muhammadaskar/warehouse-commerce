package com.ecommerce.app.product.domain.core;

import com.ecommerce.app.product.domain.core.entity.User;

public class UserDomainServiceImpl implements UserDomainService {
    @Override
    public void checkActiveUser(User user) {
        user.checkActiveUser();
    }
}
