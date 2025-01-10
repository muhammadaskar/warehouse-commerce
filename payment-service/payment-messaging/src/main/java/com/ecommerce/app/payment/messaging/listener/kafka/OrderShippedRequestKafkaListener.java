package com.ecommerce.app.payment.messaging.listener.kafka;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderShippedRequestAvroModel;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus;
import com.ecommerce.app.payment.application.service.ports.input.message.listener.order.OrderApplicationMessageListener;
import com.ecommerce.app.payment.messaging.mapper.PaymentMessagingDataMapper;
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

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final OrderApplicationMessageListener orderApplicationMessageListener;

    public OrderShippedRequestKafkaListener(PaymentMessagingDataMapper paymentMessagingDataMapper, OrderApplicationMessageListener orderApplicationMessageListener) {
        this.paymentMessagingDataMapper = paymentMessagingDataMapper;
        this.orderApplicationMessageListener = orderApplicationMessageListener;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-payment-shipped-consumer-group-id}", topics = "${payment-service.order-shipped-request-topic-name}")
    public void receive(@Payload List<OrderShippedRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        messages.forEach(orderShippedRequestAvroModel -> {
            if (orderShippedRequestAvroModel.getOrderStatus() == OrderStatus.SHIPPED) {
                orderApplicationMessageListener.updateOrderStatusToShipped(paymentMessagingDataMapper.
                        orderShippedRequestAvroModelToOrderShippedRequest(orderShippedRequestAvroModel));
            }
        });
    }
}
