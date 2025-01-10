package com.ecommerce.app.payment.application.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "payment-service")
public class PaymentConfigData {
    private String secretKey;
    private String paymentRequestTopicName;
    private String PaymentProofUploadRequestTopicName;
    private String paymentApprovedRequestTopicName;
}
