package com.ecommerce.app.order.application.service.dto.header;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AuthorizationHeader {
    private String authorization;

    @Override
    public String toString() {
        return authorization;
    }
}
