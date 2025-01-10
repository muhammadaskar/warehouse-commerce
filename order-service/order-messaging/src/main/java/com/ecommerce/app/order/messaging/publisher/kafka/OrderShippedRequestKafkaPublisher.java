package com.ecommerce.app.order.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderShippedRequestAvroModel;
import com.ecommerce.app.order.application.service.config.OrderConfigData;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderShippedRequestMessagePublisher;
import com.ecommerce.app.order.domain.core.event.OrderShippedEvent;
import com.ecommerce.app.order.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderShippedRequestKafkaPublisher implements OrderShippedRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderConfigData orderConfigData;
    private final KafkaProducer<String, OrderShippedRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    public OrderShippedRequestKafkaPublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderConfigData orderConfigData, KafkaProducer<String, OrderShippedRequestAvroModel> kafkaProducer, KafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderConfigData = orderConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderShippedEvent event) {
        String orderId = event.getOrder().getId().getValue().toString();
        log.info("Received OrderShippedEvent for order with id: {}", orderId);

        try {
            OrderShippedRequestAvroModel orderShippedRequestAvroModel = orderMessagingDataMapper.orderShippedEventToOrderShippedRequestAvroModel(event);

            kafkaProducer.send(orderConfigData.getOrderShippedRequestTopicName(),
                    orderId,
                    orderShippedRequestAvroModel,
                    orderKafkaMessageHelper.
                            getKafkaCallback(orderConfigData.getOrderShippedRequestTopicName(),
                                    orderShippedRequestAvroModel,
                                    orderId,
                                    "orderShippedRequestAvroModel"));

            log.info("Publishing order shipped event for order with id: {}", orderShippedRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error occurred while preparing to publish order shipped event for order with id: {}", event.getOrder().getId().getValue(), e);
        }
    }
}
