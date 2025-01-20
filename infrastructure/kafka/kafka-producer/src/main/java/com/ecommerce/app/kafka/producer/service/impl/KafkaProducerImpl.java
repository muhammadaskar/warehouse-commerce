package com.ecommerce.app.kafka.producer.service.impl;

import com.ecommerce.app.kafka.producer.exception.KafkaProducerException;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {
    private final KafkaTemplate kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {
            // Add callback using an explicit implementation
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, key, message);
            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    // handle failure
                    log.warn("Unable to send message=[{}] due to : {}", message, throwable.getMessage());
                } else {
                    // handle success
                    log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
                }
            });
        } catch (KafkaException e) {
            log.error("Error on kafka producer with key: {}, message: {} and exception: {}", key, message, e.getMessage());
            throw new KafkaProducerException("Error on kafka producer with key: " + key + " and message: " + message);
        }
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
