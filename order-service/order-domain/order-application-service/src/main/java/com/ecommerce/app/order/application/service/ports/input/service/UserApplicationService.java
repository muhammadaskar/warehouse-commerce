package com.ecommerce.app.order.application.service.ports.input.service;


import com.ecommerce.app.order.domain.core.entity.User;

public interface UserApplicationService {
    User checkActiveUser(String userId);
}
