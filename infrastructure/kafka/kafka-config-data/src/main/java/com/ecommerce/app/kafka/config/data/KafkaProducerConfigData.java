package com.ecommerce.app.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
public class KafkaProducerConfigData {
    private String keySerializerClass;
    private String valueSerializerClass;
    private String compressionType;
    private String acks;
    private Integer batchSize = 16384;
    private Integer batchSizeBoostFactor = 2;
    private Integer lingerMs;
    private Integer requestTimeoutMs;
    private Integer retryCount;
}
