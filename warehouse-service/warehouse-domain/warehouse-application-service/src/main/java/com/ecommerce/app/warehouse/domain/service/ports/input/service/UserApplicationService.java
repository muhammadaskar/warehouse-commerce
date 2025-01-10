package com.ecommerce.app.warehouse.domain.service.ports.input.service;

import com.ecommerce.app.warehouse.domain.core.entity.User;

public interface UserApplicationService {
    User checkActiveUser(String userId);
}
