package com.ecommerce.app.payment.messaging.listener.kafka;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderConfirmedRequestAvroModel;
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
public class OrderConfirmedRequestKafkaListener implements KafkaConsumer<OrderConfirmedRequestAvroModel> {

    private final OrderApplicationMessageListener orderApplicationMessageListener;
    private final PaymentMessagingDataMapper paymentMessagingDataMapper;

    public OrderConfirmedRequestKafkaListener(OrderApplicationMessageListener orderApplicationMessageListener, PaymentMessagingDataMapper paymentMessagingDataMapper) {
        this.orderApplicationMessageListener = orderApplicationMessageListener;
        this.paymentMessagingDataMapper = paymentMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-confirmed-consumer-group-id-in-payment-service}", topics = "${payment-service.order-confirmed-request-topic-name}")
    public void receive(@Payload List<OrderConfirmedRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        messages.forEach(orderConfirmedRequestAvroModel -> {
            log.info("Received order confirmed request: {}", orderConfirmedRequestAvroModel);
            if (orderConfirmedRequestAvroModel.getOrderStatus() == OrderStatus.CONFIRMED) {
                orderApplicationMessageListener.updateOrderStatusToConfirmed(paymentMessagingDataMapper.
                        orderConfirmedRequestAvroModelToOrderConfirmedRequest(orderConfirmedRequestAvroModel));
            }
        });
    }
}
