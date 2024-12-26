package com.ecommerce.app.user.application.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreatePasswordResponse {
    private final String message;
}
