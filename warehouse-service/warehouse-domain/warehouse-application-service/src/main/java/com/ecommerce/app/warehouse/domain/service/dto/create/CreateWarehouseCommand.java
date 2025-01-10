package com.ecommerce.app.warehouse.domain.service.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateWarehouseCommand {

    private final String name;
    private final String street;
    private final String postalCode;
    private final String city;
    private final String latitude;
    private final String longitude;
}
