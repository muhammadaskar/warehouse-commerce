package com.ecommerce.app.warehouse.domain.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "warehouse-service")
public class WarehouseServiceConfigData {
    private String warehouseCreateTopicName;
}
