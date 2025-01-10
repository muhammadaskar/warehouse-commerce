package com.ecommerce.app.warehouse.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderShippedResponseAvroModel;
import com.ecommerce.app.warehouse.domain.core.event.OrderShippedResponseEvent;
import com.ecommerce.app.warehouse.domain.service.config.WarehouseServiceConfigData;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.order.OrderShippedResponseMessagePublisher;
import com.ecommerce.app.warehouse.messaging.mapper.WarehouseMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderShippedResponseKafkaPublisher implements OrderShippedResponseMessagePublisher {

    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;
    private final KafkaProducer<String, OrderShippedResponseAvroModel> kafkaProducer;
    private final WarehouseServiceConfigData warehouseServiceConfigData;
    private final KafkaMessageHelper warehouseKafkaMessageHelper;

    public OrderShippedResponseKafkaPublisher(WarehouseMessagingDataMapper warehouseMessagingDataMapper, KafkaProducer<String, OrderShippedResponseAvroModel> kafkaProducer, WarehouseServiceConfigData warehouseServiceConfigData, KafkaMessageHelper warehouseKafkaMessageHelper) {
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.warehouseServiceConfigData = warehouseServiceConfigData;
        this.warehouseKafkaMessageHelper = warehouseKafkaMessageHelper;
    }

    @Override
    public void publish(OrderShippedResponseEvent event) {
        String orderId = event.getOrder().getId().getValue().toString();
        log.info("Received OrderShippedResponseEvent for order with id: {}", event.getOrder().getId().getValue());

        try {
            OrderShippedResponseAvroModel orderShippedResponseAvroModel = warehouseMessagingDataMapper.orderShippedEventToOrderShippedResponseAvroModel(event);

            kafkaProducer.send(warehouseServiceConfigData.getOrderShippedResponseTopicName(),
                    orderId,
                    orderShippedResponseAvroModel,
                    warehouseKafkaMessageHelper.
                            getKafkaCallback(warehouseServiceConfigData.getOrderShippedResponseTopicName(),
                                    orderShippedResponseAvroModel,
                                    orderId,
                                    "orderShippedResponseAvroModel"));

            log.info("Publishing order shipped response event for order with id: {}", orderShippedResponseAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error occurred while preparing to publish order shipped response event for order with id: {}", orderId, e);
        }
    }
}
