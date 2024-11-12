package com.ecommerce.app.user.application.service.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WarehouseCreate {
    @JsonProperty("id")
    private final String warehouseId;
//    @JsonProperty("name")
//    private final String name;
}
