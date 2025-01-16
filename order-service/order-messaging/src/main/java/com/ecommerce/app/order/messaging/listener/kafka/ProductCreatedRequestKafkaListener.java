package com.ecommerce.app.order.messaging.listener.kafka;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.ProductCreatedRequestAvroModel;
import com.ecommerce.app.order.application.service.ports.input.message.listener.product.ProductApplicationMessageListener;
import com.ecommerce.app.order.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductCreatedRequestKafkaListener implements KafkaConsumer<ProductCreatedRequestAvroModel> {

    private final ProductApplicationMessageListener productApplicationMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public ProductCreatedRequestKafkaListener(ProductApplicationMessageListener productApplicationMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.productApplicationMessageListener = productApplicationMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(groupId = "${kafka-consumer-config.product-order-created-consumer-group-id}", topics = "${order-service.product-created-topic-name}")
    public void receive(@Payload List<ProductCreatedRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        messages.forEach(productCreatedRequestAvroModel -> {
            log.info("Received productCreatedRequestAvroModel: {}", productCreatedRequestAvroModel);
            productApplicationMessageListener.createProduct(orderMessagingDataMapper.
                    productCreatedRequestAvroModelToProductCreatedRequest(productCreatedRequestAvroModel));
        });
    }
}
