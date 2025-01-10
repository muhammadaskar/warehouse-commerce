package com.ecommerce.app.warehouse.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.StockCreatedRequestAvroModel;
import com.ecommerce.app.warehouse.domain.core.event.StockCreatedEvent;
import com.ecommerce.app.warehouse.domain.service.config.WarehouseServiceConfigData;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockCreatedMessagePublisher;
import com.ecommerce.app.warehouse.messaging.mapper.WarehouseMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockCreatedKafkaPublisher implements StockCreatedMessagePublisher {

    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;
    private final KafkaProducer<String, StockCreatedRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper warehouseKafkaMessageHelper;
    private final WarehouseServiceConfigData warehouseConfigData;

    public StockCreatedKafkaPublisher(WarehouseMessagingDataMapper warehouseMessagingDataMapper, KafkaProducer<String, StockCreatedRequestAvroModel> kafkaProducer, KafkaMessageHelper warehouseKafkaMessageHelper, WarehouseServiceConfigData warehouseConfigData) {
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.warehouseKafkaMessageHelper = warehouseKafkaMessageHelper;
        this.warehouseConfigData = warehouseConfigData;
    }

    @Override
    public void publish(StockCreatedEvent event) {
        String stockId = event.getStock().getId().getValue().toString();
        log.info("Received StockCreatedEvent for stock with id: {}", stockId);

        try {
            StockCreatedRequestAvroModel stockCreatedRequestAvroModel = warehouseMessagingDataMapper.stockCreatedEventToStockCreatedRequestAvroModel(event);

            kafkaProducer.send(warehouseConfigData.getStockCreatedTopicName(),
                    stockId,
                    stockCreatedRequestAvroModel,
                    warehouseKafkaMessageHelper.
                            getKafkaCallback(warehouseConfigData.getStockCreatedTopicName(),
                                    stockCreatedRequestAvroModel,
                                    stockId,
                                    "stockCreatedRequestAvroModel"));

            log.info("Publishing stock created event for stock with id: {}", stockCreatedRequestAvroModel.getStockId());
        } catch (Exception e) {
            log.error("Error occurred while preparing to publish stock created event for stock with id: {}", event.getStock().getId().getValue(), e);
        }
    }
}
