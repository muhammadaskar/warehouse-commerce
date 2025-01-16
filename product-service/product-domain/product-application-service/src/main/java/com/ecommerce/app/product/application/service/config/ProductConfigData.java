package com.ecommerce.app.product.application.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "product-service")
public class ProductConfigData {
    private String secretKey;
    private String productCreatedTopicName;
}
