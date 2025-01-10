package com.ecommerce.app.warehouse.domain.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MessageResponse {
    private final String message;
}
