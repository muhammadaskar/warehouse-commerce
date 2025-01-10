package com.ecommerce.app.user.application.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class LoginUserResponse {
    private final UUID userId;
    private final String token;
    private final String email;
    private final String role;
    private final UUID warehouseId;
    private final String message;
}
