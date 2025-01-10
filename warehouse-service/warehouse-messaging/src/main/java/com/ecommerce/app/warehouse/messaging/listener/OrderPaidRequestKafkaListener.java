package com.ecommerce.app.warehouse.messaging.listener;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderPaidRequestAvroModel;
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
public class OrderPaidRequestKafkaListener implements KafkaConsumer<OrderPaidRequestAvroModel> {

    private final OrderApplicationMessageListener orderApplicationMessageListener;
    private final WarehouseMessagingDataMapper warehouseMessagingDataMapper;

    public OrderPaidRequestKafkaListener(OrderApplicationMessageListener orderApplicationMessageListener, WarehouseMessagingDataMapper warehouseMessagingDataMapper) {
        this.orderApplicationMessageListener = orderApplicationMessageListener;
        this.warehouseMessagingDataMapper = warehouseMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-paid-consumer-group-id}", topics = "${warehouse-service.order-paid-request-topic-name}")
    public void receive(@Payload List<OrderPaidRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        messages.forEach(orderPaidRequestAvroModel -> {
            if (orderPaidRequestAvroModel.getOrderStatus() == OrderStatus.APPROVED) {
                log.debug("Processing approved order for order id: {}", orderPaidRequestAvroModel.getOrderId());
                orderApplicationMessageListener.processOrder(warehouseMessagingDataMapper.
                        orderPaidRequestAvroModelToOrderPaidRequest(orderPaidRequestAvroModel));
            }
        });
    }
}
