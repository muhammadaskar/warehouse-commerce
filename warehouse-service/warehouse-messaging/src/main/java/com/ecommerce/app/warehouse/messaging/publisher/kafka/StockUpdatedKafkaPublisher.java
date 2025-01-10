package com.ecommerce.app.warehouse.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.StockUpdatedRequestAvroModel;
import com.ecommerce.app.warehouse.domain.core.event.StockUpdatedEvent;
import com.ecommerce.app.warehouse.domain.service.config.WarehouseServiceConfigData;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockUpdatedMessagePublisher;
import com.ecommerce.app.warehouse.messaging.mapper.WarehouseMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockUpdatedKafkaPublisher implements StockUpdatedMessagePublisher {

    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;
    private final KafkaProducer<String, StockUpdatedRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;
    private final WarehouseServiceConfigData warehouseServiceConfigData;

    public StockUpdatedKafkaPublisher(WarehouseMessagingDataMapper warehouseMessagingDataMapper, KafkaProducer<String, StockUpdatedRequestAvroModel> kafkaProducer, KafkaMessageHelper kafkaMessageHelper, WarehouseServiceConfigData warehouseServiceConfigData) {
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.kafkaMessageHelper = kafkaMessageHelper;
        this.warehouseServiceConfigData = warehouseServiceConfigData;
    }

    @Override
    public void publish(StockUpdatedEvent event) {
        String stockId = event.getStock().getId().getValue().toString();
        log.info("Received StockUpdatedEvent for stock with id: {}", stockId);

        try {
            StockUpdatedRequestAvroModel stockUpdatedRequestAvroModel = warehouseMessagingDataMapper.stockUpdatedEventToStockUpdatedRequestAvroModel(event);

            kafkaProducer.send(warehouseServiceConfigData.getStockUpdatedTopicName(),
                    stockId,
                    stockUpdatedRequestAvroModel,
                    kafkaMessageHelper.getKafkaCallback(warehouseServiceConfigData.getStockUpdatedTopicName(),
                            stockUpdatedRequestAvroModel,
                            stockId,
                            "stockUpdatedRequestAvroModel"));

            log.info("Publishing stock updated event for stock with id: {}", stockUpdatedRequestAvroModel.getStockId());
        } catch (Exception e) {
            log.error("Error occurred while preparing to publish stock updated event for stock with id: {}", event.getStock().getId().getValue(), e);
        }
    }
}
