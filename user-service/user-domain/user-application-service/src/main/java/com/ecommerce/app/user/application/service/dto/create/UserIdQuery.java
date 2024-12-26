package com.ecommerce.app.user.application.service.dto.create;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserIdQuery {
    @NotNull
    private final UUID userId;
}
