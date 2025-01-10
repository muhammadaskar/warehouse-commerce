package com.ecommerce.app.payment.domain.core;

import com.ecommerce.app.payment.domain.core.entity.User;

public class UserDomainServiceImpl implements UserDomainService{
    @Override
    public void checkActiveUser(User user) {
        user.checkActiveUser();
    }
}
