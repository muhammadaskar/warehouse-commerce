package com.ecommerce.app.user.application.service.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateUserResponse {
    @NotNull
    private final UUID userId;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private final String token;
    @NotNull
    private final String message;
}
