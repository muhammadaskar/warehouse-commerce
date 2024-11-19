package com.ecommerce.app.user.application.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "user-service")
public class UserServiceConfigData {
    private String warehouseAdminCreateTopicName;
}
