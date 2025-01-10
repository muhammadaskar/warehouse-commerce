package com.ecommerce.app.order.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderProcessedRequestAvroModel;
import com.ecommerce.app.order.application.service.config.OrderConfigData;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderProcessedRequestMessagePublisher;
import com.ecommerce.app.order.domain.core.event.OrderProcessedEvent;
import com.ecommerce.app.order.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderProcessedRequestKafkaPublisher implements OrderProcessedRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderConfigData orderConfigData;
    private final KafkaProducer<String, OrderProcessedRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    public OrderProcessedRequestKafkaPublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderConfigData orderConfigData, KafkaProducer<String, OrderProcessedRequestAvroModel> kafkaProducer, KafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderConfigData = orderConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderProcessedEvent event) {
        String orderId = event.getOrder().getId().getValue().toString();
        log.info("Received order processed event for order id: {}", orderId);

        try {

            OrderProcessedRequestAvroModel orderProcessedRequestAvroModel = orderMessagingDataMapper.
                    orderProcessedEventToOrderProcessedRequestAvroModel(event);

            kafkaProducer.send(orderConfigData.getOrderProcessedRequestTopicName(),
                    orderId,
                    orderProcessedRequestAvroModel,
                    orderKafkaMessageHelper.
                            getKafkaCallback(orderConfigData.getOrderProcessedRequestTopicName(),
                                    orderProcessedRequestAvroModel,
                                    orderId,
                                    "paymentRequestAvroModel"));
            log.info("Order processed event published successfully for order id: {}", orderId);

        } catch (Exception e) {
            log.error("An error occurred while publishing order processed event for order id: {}", orderId, e);
        }

    }
}
