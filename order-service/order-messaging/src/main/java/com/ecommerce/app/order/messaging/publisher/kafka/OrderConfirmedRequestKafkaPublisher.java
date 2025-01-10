package com.ecommerce.app.order.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderConfirmedRequestAvroModel;
import com.ecommerce.app.order.application.service.config.OrderConfigData;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.order.OrderConfirmedRequestMessagePublisher;
import com.ecommerce.app.order.domain.core.event.OrderConfirmedEvent;
import com.ecommerce.app.order.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConfirmedRequestKafkaPublisher implements OrderConfirmedRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final KafkaProducer<String, OrderConfirmedRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;
    private final OrderConfigData orderConfigData;

    public OrderConfirmedRequestKafkaPublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                               KafkaProducer<String, OrderConfirmedRequestAvroModel> kafkaProducer,
                                               KafkaMessageHelper orderKafkaMessageHelper,
                                               OrderConfigData orderConfigData) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
        this.orderConfigData = orderConfigData;
    }

    @Override
    public void publish(OrderConfirmedEvent event) {
        String orderId = event.getOrder().getId().getValue().toString();
        log.info("Received OrderConfirmedEvent for order with id: {}", orderId);

        try {
            OrderConfirmedRequestAvroModel orderConfirmedRequestAvroModel = orderMessagingDataMapper.
                    orderConfirmedRequestEventToOrderConfirmedRequestAvroModel(event);

            kafkaProducer.send(orderConfigData.getOrderConfirmedRequestTopicName(),
                    orderId,
                    orderConfirmedRequestAvroModel,
                    orderKafkaMessageHelper.
                            getKafkaCallback(orderConfigData.getOrderConfirmedRequestTopicName(),
                                    orderConfirmedRequestAvroModel,
                                    orderId,
                                    "orderConfirmedRequestAvroModel"));

            log.info("Publishing order confirmed event for order with id: {}", orderConfirmedRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error occurred while preparing to publish order confirmed event for order with id: {}", event.getOrder().getId().getValue(), e);
        }
    }
}
