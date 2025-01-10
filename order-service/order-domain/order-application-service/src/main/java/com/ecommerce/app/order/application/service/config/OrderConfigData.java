package com.ecommerce.app.order.application.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "order-service")
public class OrderConfigData {
    private String secretKey;
    private String paymentRequestTopicName;
    private String paymentProofUploadResponseTopicName;
    private String orderPaidRequestTopicName;
    private String orderProcessedRequestTopicName;
    private String orderShippedRequestTopicName;
    private String orderConfirmedRequestTopicName;
}
