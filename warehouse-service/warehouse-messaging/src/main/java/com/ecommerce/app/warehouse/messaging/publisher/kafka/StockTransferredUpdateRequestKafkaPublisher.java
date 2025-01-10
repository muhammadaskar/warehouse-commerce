package com.ecommerce.app.warehouse.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.StockTransferredUpdateAvroModel;
import com.ecommerce.app.warehouse.domain.core.event.StockTransferredUpdateEvent;
import com.ecommerce.app.warehouse.domain.service.config.WarehouseServiceConfigData;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockTransferredUpdateMessagePublisher;
import com.ecommerce.app.warehouse.messaging.mapper.WarehouseMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockTransferredUpdateRequestKafkaPublisher implements StockTransferredUpdateMessagePublisher {

    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;
    private final WarehouseServiceConfigData warehouseServiceConfigData;
    private final KafkaProducer<String, StockTransferredUpdateAvroModel> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;

    public StockTransferredUpdateRequestKafkaPublisher(WarehouseMessagingDataMapper warehouseMessagingDataMapper, WarehouseServiceConfigData warehouseServiceConfigData, KafkaProducer<String, StockTransferredUpdateAvroModel> kafkaProducer, KafkaMessageHelper kafkaMessageHelper) {
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
        this.warehouseServiceConfigData = warehouseServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(StockTransferredUpdateEvent event) {

        String warehouseIdTo = event.getStockReceived().getWarehouseId().getValue().toString();

        try {
            StockTransferredUpdateAvroModel stockTransferredUpdateAvroModel = warehouseMessagingDataMapper.stockTransferredUpdateEventToStockTransferredUpdateAvroModel(event);
            kafkaProducer.send(warehouseServiceConfigData.getStockTransferredUpdateTopicName(),
                    warehouseIdTo,
                    stockTransferredUpdateAvroModel,
                    kafkaMessageHelper.getKafkaCallback(warehouseServiceConfigData.getStockTransferredUpdateTopicName(),
                            stockTransferredUpdateAvroModel,
                            warehouseIdTo,
                            "StockTransferredUpdateAvroModel"));
            log.info("Stock transferred update event published to kafka: {}", stockTransferredUpdateAvroModel);
        } catch (Exception e) {
            log.error("Error occurred while publishing stock transferred update event to kafka", e);
        }
    }
}
