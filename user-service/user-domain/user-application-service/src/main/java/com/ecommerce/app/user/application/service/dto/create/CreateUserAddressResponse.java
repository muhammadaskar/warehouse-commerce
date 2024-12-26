package com.ecommerce.app.user.application.service.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateUserAddressResponse {
    @NotNull
    private final UUID addressId;
    @NotNull
    private final String message;
}
