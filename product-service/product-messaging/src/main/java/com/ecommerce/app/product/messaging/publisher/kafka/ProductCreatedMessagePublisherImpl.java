package com.ecommerce.app.product.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.ProductCreatedRequestAvroModel;
import com.ecommerce.app.product.application.service.config.ProductConfigData;
import com.ecommerce.app.product.application.service.ports.output.message.publisher.product.ProductCreatedMessagePublisher;
import com.ecommerce.app.product.domain.core.event.ProductCreatedEvent;
import com.ecommerce.app.product.messaging.mapper.ProductMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductCreatedMessagePublisherImpl implements ProductCreatedMessagePublisher {

    private final ProductMessagingDataMapper productMessagingDataMapper;
    private final ProductConfigData productConfigData;
    private final KafkaProducer<String, ProductCreatedRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper productKafkaMessageHelper;

    public ProductCreatedMessagePublisherImpl(ProductMessagingDataMapper productMessagingDataMapper, ProductConfigData productConfigData, KafkaProducer<String, ProductCreatedRequestAvroModel> kafkaProducer, KafkaMessageHelper productKafkaMessageHelper) {
        this.productMessagingDataMapper = productMessagingDataMapper;
        this.productConfigData = productConfigData;
        this.kafkaProducer = kafkaProducer;
        this.productKafkaMessageHelper = productKafkaMessageHelper;
    }

    @Override
    public void publish(ProductCreatedEvent event) {
        String productId = event.getProduct().getId().getValue().toString();
        log.info("Received ProductCreatedEvent for product id: {}", productId);
        try {
            log.debug("Mapping ProductCreatedEvent to ProductCreatedRequestAvroModel");
            ProductCreatedRequestAvroModel productCreatedRequestAvroModel = productMessagingDataMapper
                    .productCreatedEventToProductCreatedAvroModel(event);
            log.debug("Mapped ProductCreatedEvent to ProductCreatedRequestAvroModel");

            log.info("ProductCreatedRequestAvroModel : {}", productCreatedRequestAvroModel);

            kafkaProducer.send(productConfigData.getProductCreatedTopicName(),
                    productId,
                    productCreatedRequestAvroModel,
                    productKafkaMessageHelper
                            .getKafkaCallback(productConfigData.getProductCreatedTopicName(),
                                    productCreatedRequestAvroModel,
                                    productId,
                                    "ProductCreatedRequestAvroModel"));
            log.info("ProductCreatedRequestAvroModel has been sent to product id: {}", productId);
        } catch (Exception e) {
            log.error(new StringBuilder()
                    .append("Error while sending ProductCreatedRequestAvroModel message")
                    .append(" to kafka with product id: {}").toString(), productId, e.getMessage());
        }
    }
}
