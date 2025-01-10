package com.ecommerce.app.order.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.PaymentRequestAvroModel;
import com.ecommerce.app.order.application.service.config.OrderConfigData;
import com.ecommerce.app.order.domain.core.event.OrderCreatedEvent;
import com.ecommerce.app.order.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class OrderCreatedPaymentRequestKafkaPublisher implements com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderConfigData orderConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    public OrderCreatedPaymentRequestKafkaPublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderConfigData orderConfigData, KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, KafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderConfigData = orderConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderCreatedEvent event) {
        String orderId = event.getOrder().getId().getValue().toString();
        log.info("Received OrderCreatedEvent for order with id: {}", orderId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(event);

            CompletableFuture.runAsync(() -> {
                kafkaProducer.send(orderConfigData.getPaymentRequestTopicName(),
                        orderId,
                        paymentRequestAvroModel,
                        orderKafkaMessageHelper.
                                getKafkaCallback(orderConfigData.getPaymentRequestTopicName(),
                                        paymentRequestAvroModel,
                                        orderId,
                                        "paymentRequestAvroModel"));

                log.info("Publishing order created event for payment with order id: {}", paymentRequestAvroModel.getOrderId());
            }).exceptionally(e -> {
                log.error("Error occurred while publishing order created event for payment with order id: {}", event.getOrder().getId().getValue(), e);
                return null;
            });
        } catch (Exception e) {
            log.error("Error occurred while preparing to publish order created event for payment with order id: {}", event.getOrder().getId().getValue(), e);
        }
    }
}
