package com.ecommerce.app.user.application.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginUserResponse {
    private final String token;
    private final String email;
    private final String role;
    private final String message;
}
