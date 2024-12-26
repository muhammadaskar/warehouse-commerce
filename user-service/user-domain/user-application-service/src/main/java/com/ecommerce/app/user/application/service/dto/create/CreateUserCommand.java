package com.ecommerce.app.user.application.service.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateUserCommand {
    @NotNull
    private String username;
    @NotNull
    private String email;
}
