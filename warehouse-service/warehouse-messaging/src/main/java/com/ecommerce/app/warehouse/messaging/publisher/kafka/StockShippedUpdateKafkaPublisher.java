package com.ecommerce.app.warehouse.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.StockShippedUpdateResponseAvroModel;
import com.ecommerce.app.warehouse.domain.core.event.StockShippedUpdateEvent;
import com.ecommerce.app.warehouse.domain.service.config.WarehouseServiceConfigData;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.StockShippedUpdateMessagePublisher;
import com.ecommerce.app.warehouse.messaging.mapper.WarehouseMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class StockShippedUpdateKafkaPublisher implements StockShippedUpdateMessagePublisher {

    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;
    private final KafkaProducer<String, StockShippedUpdateResponseAvroModel> kafkaProducer;
    private final KafkaMessageHelper warehouseKafkaMessageHelper;
    private final WarehouseServiceConfigData warehouseServiceConfigData;

    public StockShippedUpdateKafkaPublisher(WarehouseMessagingDataMapper warehouseMessagingDataMapper,
                                            KafkaProducer<String, StockShippedUpdateResponseAvroModel> kafkaProducer,
                                            KafkaMessageHelper warehouseKafkaMessageHelper,
                                            WarehouseServiceConfigData warehouseServiceConfigData) {
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.warehouseKafkaMessageHelper = warehouseKafkaMessageHelper;
        this.warehouseServiceConfigData = warehouseServiceConfigData;
    }

    @Override
    public void publish(StockShippedUpdateEvent event) {
        String id = UUID.randomUUID().toString();

        try {
            StockShippedUpdateResponseAvroModel stockShippedUpdateResponseAvroModel = warehouseMessagingDataMapper.stockShippedUpdateEventToStockShippedUpdateResponseAvroModel(event);

            kafkaProducer.send(warehouseServiceConfigData.getStockShippedUpdateTopicName(),
                    id,
                    stockShippedUpdateResponseAvroModel,
                    warehouseKafkaMessageHelper.
                            getKafkaCallback(warehouseServiceConfigData.getStockShippedUpdateTopicName(),
                                    stockShippedUpdateResponseAvroModel,
                                    id,
                                    "stockShippedUpdateResponseAvroModel"));

            log.info("Publishing stock shipped update with id: {}", id);
        } catch (Exception e) {
            log.error("Error occurred while preparing to publish stock shipped update with id: {}", id, e);
        }
    }
}
