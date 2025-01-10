package com.ecommerce.app.order.application.service.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrderAddress {
    @NotNull
    private String street;
    @NotNull
    private String postalCode;
    @NotNull
    private String city;
    @NotNull
    private String latitude;
    @NotNull
    private String longitude;
}
