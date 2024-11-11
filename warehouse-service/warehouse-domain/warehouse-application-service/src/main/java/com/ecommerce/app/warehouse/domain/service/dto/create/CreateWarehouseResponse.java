package com.ecommerce.app.warehouse.domain.service.dto.create;

import com.ecommerce.app.common.domain.valueobject.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateWarehouseResponse {
    @NotNull
    private final UUID warehouseId;
    @NotNull
    private final String warehouseName;
    @NotNull
    private final Address warehouseAddress;
    @NotNull
    private final String message;
}
