package com.ecommerce.app.warehouse.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel;
import com.ecommerce.app.warehouse.domain.core.event.WarehouseCreatedEvent;
import com.ecommerce.app.warehouse.domain.service.config.WarehouseServiceConfigData;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.warehouse.WarehouseCreatedRequestMessagePublisher;
import com.ecommerce.app.warehouse.messaging.mapper.WarehouseMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateWarehouseKafkaMessagePublisher implements WarehouseCreatedRequestMessagePublisher {

    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;
    private final WarehouseServiceConfigData warehouseServiceConfigData;
    private final KafkaProducer<String, WarehouseRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper warehouseKafkaMessageHelper;

    public CreateWarehouseKafkaMessagePublisher(WarehouseMessagingDataMapper warehouseMessagingDataMapper, WarehouseServiceConfigData warehouseServiceConfigData, KafkaProducer<String, WarehouseRequestAvroModel> kafkaProducer, KafkaMessageHelper warehouseKafkaMessageHelper) {
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
        this.warehouseServiceConfigData = warehouseServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.warehouseKafkaMessageHelper = warehouseKafkaMessageHelper;
    }

    @Override
    public void publish(WarehouseCreatedEvent domainEvent) {
        String warehouseId = domainEvent.getWarehouse().getId().getValue().toString();
        log.info("Received WarehouseCreatedEvent for warehouse id: {}", warehouseId);
        try {
            WarehouseRequestAvroModel warehouseRequestAvroModel = warehouseMessagingDataMapper
                    .warehouseCreatedEventToOtherRequestAvroModel(domainEvent);

             kafkaProducer.send(warehouseServiceConfigData.getWarehouseCreateTopicName(),
                     warehouseId,
                    warehouseRequestAvroModel,
                    warehouseKafkaMessageHelper
                            .getKafkaCallback(warehouseServiceConfigData.getWarehouseCreateTopicName(),
                                    warehouseRequestAvroModel,
                                    warehouseId,
                                    "WarehouseRequestAvroModel"));
            log.info("WarehouseRequestAvroModel has been sent to warehouse id: {}", warehouseRequestAvroModel.getWarehouseId());
        } catch (Exception e) {
            log.error(new StringBuilder()
                    .append("Error while sending WarehouseRequestAvroModel message")
                    .append(" to kafka with warehouse id: {}").toString(), warehouseId, e.getMessage());
        }
    }
}