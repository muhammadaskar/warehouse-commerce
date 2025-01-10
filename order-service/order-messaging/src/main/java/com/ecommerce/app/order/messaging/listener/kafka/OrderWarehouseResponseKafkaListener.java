package com.ecommerce.app.order.messaging.listener.kafka;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderWarehouseResponseAvroModel;
import com.ecommerce.app.order.application.service.ports.input.message.listener.warehouse.OrderWarehouseApplicationMessageListener;
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
public class OrderWarehouseResponseKafkaListener implements KafkaConsumer<OrderWarehouseResponseAvroModel> {

    private final OrderWarehouseApplicationMessageListener orderWarehouseApplicationMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public OrderWarehouseResponseKafkaListener(OrderWarehouseApplicationMessageListener orderWarehouseApplicationMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.orderWarehouseApplicationMessageListener = orderWarehouseApplicationMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-warehouse-response-consumer-group-id}", topics = "${order-service.order-warehouse-response-topic-name}")
    public void receive(@Payload List<OrderWarehouseResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.debug("{} number of order warehouse responses received with keys {}, partitions {} and offsets {}",
                messages,
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(orderWarehouseResponseAvroModel -> {
            log.info("Processing order warehouse response for order id: {}",
                    orderWarehouseResponseAvroModel.getOrderId());
            if (orderWarehouseResponseAvroModel.getOrderStatus() == OrderStatus.PROCESSED) {
                log.debug("Order warehouse response for order id: {} is processed",
                        orderWarehouseResponseAvroModel.getOrderId());
                orderWarehouseApplicationMessageListener.processedOrder(orderMessagingDataMapper
                        .orderWarehouseResponseAvroModelToOrderWarehouseResponse(orderWarehouseResponseAvroModel));
            } else if (orderWarehouseResponseAvroModel.getOrderStatus() == OrderStatus.CANCELLED) {
                log.debug("Order warehouse response for order id: {} is rejected",
                        orderWarehouseResponseAvroModel.getOrderId());
            }
        });
    }
}
