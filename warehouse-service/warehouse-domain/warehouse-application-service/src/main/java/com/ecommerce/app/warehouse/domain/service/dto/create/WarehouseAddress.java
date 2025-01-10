package com.ecommerce.app.warehouse.domain.service.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class WarehouseAddress {
    @NotNull
    private final String street;
    private final String postalCode;
    private final String city;
    private final String latitude;
    private final String longitude;
}
