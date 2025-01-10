package com.ecommerce.app.order.domain.core;

import com.ecommerce.app.order.domain.core.entity.User;

import java.util.UUID;

public class UserDomainServiceImpl implements UserDomainService{

    @Override
    public void checkActiveUser(User user) {
        user.checkActiveUser();
    }

    @Override
    public UUID getUserId(User user) {
        return user.getId().getValue();
    }
}
