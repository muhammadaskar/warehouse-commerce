package com.ecommerce.app.warehouse.messaging.listener;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.ProductCreatedRequestAvroModel;
import com.ecommerce.app.warehouse.domain.service.ports.input.message.listener.product.ProductApplicationMessageListener;
import com.ecommerce.app.warehouse.messaging.mapper.WarehouseMessagingDataMapper;
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
    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;

    public ProductCreatedRequestKafkaListener(ProductApplicationMessageListener productApplicationMessageListener, WarehouseMessagingDataMapper warehouseMessagingDataMapper) {
        this.productApplicationMessageListener = productApplicationMessageListener;
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.product-warehouse-created-consumer-group-id}", topics = "${warehouse-service.product-created-topic-name}")
    public void receive(@Payload List<ProductCreatedRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        messages.forEach(productCreatedRequestAvroModel -> {
            log.info("Received ProductCreatedRequestAvroModel: {}", productCreatedRequestAvroModel);
            productApplicationMessageListener.createProduct(warehouseMessagingDataMapper.
                    productCreatedRequestAvroModelToProductCreatedRequest(productCreatedRequestAvroModel));
        });
    }
}
