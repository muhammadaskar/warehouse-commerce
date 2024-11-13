package com.ecommerce.app.warehouse.domain.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateWarehouseCommand {
    @NotNull
    private final String name;
    @NotNull
    private final String street;
    @NotNull
    private final String postalCode;
    @NotNull
    private final String city;
}
