package com.ecommerce.app.user.domain.core;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.domain.core.entity.UserAddress;

public interface UserAddressDomainService {
    void addAddress(UserId userId, UserAddress userAddress);
}
