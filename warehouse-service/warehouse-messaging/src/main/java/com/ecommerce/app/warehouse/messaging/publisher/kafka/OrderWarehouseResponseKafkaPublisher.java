package com.ecommerce.app.warehouse.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderWarehouseResponseAvroModel;
import com.ecommerce.app.warehouse.domain.core.event.OrderWarehouseResponseEvent;
import com.ecommerce.app.warehouse.domain.service.config.WarehouseServiceConfigData;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.order.OrderWarehouseResponseMessagePublisher;
import com.ecommerce.app.warehouse.messaging.mapper.WarehouseMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderWarehouseResponseKafkaPublisher implements OrderWarehouseResponseMessagePublisher {

    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;
    private final WarehouseServiceConfigData warehouseServiceConfigData;
    private final KafkaProducer<String, OrderWarehouseResponseAvroModel> kafkaProducer;
    private final KafkaMessageHelper warehouseKafkaMessageHelper;

    public OrderWarehouseResponseKafkaPublisher(WarehouseMessagingDataMapper warehouseMessagingDataMapper, WarehouseServiceConfigData warehouseServiceConfigData, KafkaProducer<String, OrderWarehouseResponseAvroModel> kafkaProducer, KafkaMessageHelper warehouseKafkaMessageHelper) {
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
        this.warehouseServiceConfigData = warehouseServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.warehouseKafkaMessageHelper = warehouseKafkaMessageHelper;
    }

    @Override
    public void publish(OrderWarehouseResponseEvent event) {
        String orderId = event.getOrder().getId().getValue().toString();
        log.info("Received OrderWarehouseResponseEvent for order id: {}", orderId);
        try {
            OrderWarehouseResponseAvroModel orderWarehouseResponseAvroModel = warehouseMessagingDataMapper
                    .orderWarehouseEventToOrderWarehouseResponseAvroModel(event);

            log.info("OrderWarehouseResponseAvroModel : {}", orderWarehouseResponseAvroModel);

            kafkaProducer.send(warehouseServiceConfigData.getOrderWarehouseResponseTopicName(),
                    orderId,
                    orderWarehouseResponseAvroModel,
                    warehouseKafkaMessageHelper
                            .getKafkaCallback(warehouseServiceConfigData.getOrderWarehouseResponseTopicName(),
                                    orderWarehouseResponseAvroModel,
                                    orderId,
                                    "OrderWarehouseResponseAvroModel"));
            log.info("OrderWarehouseResponseAvroModel has been sent to order id: {}", orderId);
        } catch (Exception e) {
            log.error(new StringBuilder()
                    .append("Error while sending OrderWarehouseResponseAvroModel message")
                    .append(" to kafka with order id: {}").toString(), orderId, e.getMessage());
        }
    }
}
