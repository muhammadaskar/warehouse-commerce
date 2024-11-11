package com.ecommerce.app.warehouse.domain.service.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = CreateWarehouseCommand.CreateWarehouseCommandBuilder.class)
public class CreateWarehouseCommand {
    @NotNull
    @JsonProperty("name")
    private final String name;
    @NotNull
    @JsonProperty("street")
    private final String street;
    @NotNull
    @JsonProperty("postalCode")
    private final String postalCode;
    @NotNull
    @JsonProperty("city")
    private final String city;
}
