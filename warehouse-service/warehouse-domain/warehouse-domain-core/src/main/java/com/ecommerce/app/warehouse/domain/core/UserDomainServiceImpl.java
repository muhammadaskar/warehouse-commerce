package com.ecommerce.app.warehouse.domain.core;

import com.ecommerce.app.warehouse.domain.core.entity.User;

public class UserDomainServiceImpl implements UserDomainService{
    @Override
    public void checkActiveUser(User user) {
        user.checkActiveUser();
    }
}
