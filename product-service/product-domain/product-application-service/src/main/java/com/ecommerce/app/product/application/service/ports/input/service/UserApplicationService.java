package com.ecommerce.app.product.application.service.ports.input.service;

import com.ecommerce.app.product.domain.core.entity.User;

public interface UserApplicationService {
    User checkActiveUser(String userId);
}
