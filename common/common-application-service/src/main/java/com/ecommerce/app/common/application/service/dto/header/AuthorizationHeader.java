package com.ecommerce.app.common.application.service.dto.header;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AuthorizationHeader {
    private final String authorization;
}
