package com.ecommerce.app.payment.application.service.ports.input.service;

import com.ecommerce.app.payment.domain.core.entity.User;

public interface UserApplicationService {
    User checkActiveUser(String userId);
}
