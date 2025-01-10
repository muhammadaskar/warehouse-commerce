package com.ecommerce.app.warehouse.messaging.listener;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderShippedRequestAvroModel;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus;
import com.ecommerce.app.warehouse.domain.service.ports.input.message.listener.order.OrderApplicationMessageListener;
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
public class OrderShippedRequestKafkaListener implements KafkaConsumer<OrderShippedRequestAvroModel> {

    private final OrderApplicationMessageListener orderApplicationMessageListener;
    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;

    public OrderShippedRequestKafkaListener(OrderApplicationMessageListener orderApplicationMessageListener, WarehouseMessagingDataMapper warehouseMessagingDataMapper) {
        this.orderApplicationMessageListener = orderApplicationMessageListener;
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-warehouse-shipped-consumer-group-id}", topics = "${warehouse-service.order-shipped-request-topic-name}")
    public void receive(@Payload List<OrderShippedRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        messages.forEach(orderShippedRequestAvroModel -> {
            if (orderShippedRequestAvroModel.getOrderStatus() == OrderStatus.SHIPPED) {
                orderApplicationMessageListener.processShipping(warehouseMessagingDataMapper.
                        orderShippedRequestAvroModelToOrderShippedRequest(orderShippedRequestAvroModel));
            }
        });
    }
}
