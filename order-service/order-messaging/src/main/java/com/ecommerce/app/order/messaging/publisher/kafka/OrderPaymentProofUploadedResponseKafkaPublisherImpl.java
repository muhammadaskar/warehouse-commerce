package com.ecommerce.app.order.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel;
import com.ecommerce.app.order.application.service.config.OrderConfigData;
import com.ecommerce.app.order.application.service.ports.output.message.publisher.payment.OrderPaymentProofUploadedResponseMessagePublisher;
import com.ecommerce.app.order.domain.core.event.OrderPaymentProofUploadedEvent;
import com.ecommerce.app.order.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderPaymentProofUploadedResponseKafkaPublisherImpl implements OrderPaymentProofUploadedResponseMessagePublisher {


    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderConfigData orderConfigData;
    private final KafkaProducer<String, PaymentProofResponseAvroModel> kafkaProducer;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    public OrderPaymentProofUploadedResponseKafkaPublisherImpl(OrderMessagingDataMapper orderMessagingDataMapper, OrderConfigData orderConfigData, KafkaProducer<String, PaymentProofResponseAvroModel> kafkaProducer, KafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderConfigData = orderConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderPaymentProofUploadedEvent event) {
        String orderId = event.getOrder().getId().getValue().toString();
        log.info("Received OrderPaymentProofUploadedEvent for order with id: {}", orderId);

        try {
            PaymentProofResponseAvroModel paymentProofResponseAvroModel = orderMessagingDataMapper.orderPaymentProofUploadedEventToPaymentProofResponseAvroModel(event);

            kafkaProducer.send(orderConfigData.getPaymentProofUploadResponseTopicName(),
                    orderId,
                    paymentProofResponseAvroModel,
                    orderKafkaMessageHelper.
                            getKafkaCallback(orderConfigData.getPaymentProofUploadResponseTopicName(),
                                    paymentProofResponseAvroModel,
                                    orderId,
                                    "paymentProofResponseAvroModel"));

            log.info("Publishing order payment proof uploaded event for payment with order id: {}", paymentProofResponseAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error occurred while preparing to publish order payment proof uploaded event for payment with order id: {}", event.getOrder().getId().getValue(), e);
        }
    }
}
