package com.ecommerce.app.user.domain.core;

import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.entity.UserAddress;

public class UserAddressDomainServiceImpl implements UserAddressDomainService {
    @Override
    public void addAddress(UserId userId, UserAddress userAddress) {
        userAddress.initializeUserAddress(userId);
    }
}
