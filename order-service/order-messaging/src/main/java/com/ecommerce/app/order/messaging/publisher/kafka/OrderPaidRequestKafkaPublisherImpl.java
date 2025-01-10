package com.ecommerce.app.order.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderPaidRequestAvroModel;
import com.ecommerce.app.order.application.service.config.OrderConfigData;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderPaidEventRequestMessagePublisher;
import com.ecommerce.app.order.domain.core.event.OrderPaidEvent;
import com.ecommerce.app.order.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderPaidRequestKafkaPublisherImpl implements OrderPaidEventRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderConfigData orderConfigData;
    private final KafkaProducer<String, OrderPaidRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    public OrderPaidRequestKafkaPublisherImpl(OrderMessagingDataMapper orderMessagingDataMapper, OrderConfigData orderConfigData, KafkaProducer<String, OrderPaidRequestAvroModel> kafkaProducer, KafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderConfigData = orderConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderPaidEvent event) {
        String orderId = event.getOrder().getId().getValue().toString();
        log.info("Received OrderPaidEvent for order with id: {}", orderId);

        try {
            OrderPaidRequestAvroModel orderPaidRequestAvroModel = orderMessagingDataMapper.orderPaidEventToOrderPaidRequestAvroModel(event);

            kafkaProducer.send(orderConfigData.getOrderPaidRequestTopicName(),
                    orderId,
                    orderPaidRequestAvroModel,
                    orderKafkaMessageHelper.
                            getKafkaCallback(orderConfigData.getOrderPaidRequestTopicName(),
                                    orderPaidRequestAvroModel,
                                    orderId,
                                    "orderPaidRequestAvroModel"));

            log.info("Publishing order paid event for payment with order id: {}", orderPaidRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error occurred while preparing to publish order paid event for payment with order id: {}", event.getOrder().getId().getValue(), e);
        }
    }
}
