package com.ecommerce.app.payment.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.PaymentApprovedRequestAvroModel;
import com.ecommerce.app.payment.application.service.config.PaymentConfigData;
import com.ecommerce.app.payment.application.service.ports.output.message.publisher.payment.PaymentApprovedMessagePublisher;
import com.ecommerce.app.payment.domain.core.event.PaymentApprovedEvent;
import com.ecommerce.app.payment.messaging.mapper.PaymentMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class PaymentApprovedMessagePublisherImpl implements PaymentApprovedMessagePublisher {

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final PaymentConfigData paymentConfigData;
    private final KafkaProducer<String, PaymentApprovedRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper paymentKafkaMessageHelper;

    public PaymentApprovedMessagePublisherImpl(PaymentMessagingDataMapper paymentMessagingDataMapper, PaymentConfigData paymentConfigData, KafkaProducer<String, PaymentApprovedRequestAvroModel> kafkaProducer, KafkaMessageHelper paymentKafkaMessageHelper) {
        this.paymentMessagingDataMapper = paymentMessagingDataMapper;
        this.paymentConfigData = paymentConfigData;
        this.kafkaProducer = kafkaProducer;
        this.paymentKafkaMessageHelper = paymentKafkaMessageHelper;
    }

    @Override
    public void publish(PaymentApprovedEvent event) {

        String paymentId = event.getPayment().getId().getValue().toString();

        try {
            PaymentApprovedRequestAvroModel paymentApprovedRequestAvroModel =
                    paymentMessagingDataMapper.paymentApprovedEventToPaymentApprovedRequestAvroModel(event);


            kafkaProducer.send(paymentConfigData.getPaymentApprovedRequestTopicName(),
                    paymentId,
                    paymentApprovedRequestAvroModel,
                    paymentKafkaMessageHelper
                            .getKafkaCallback(paymentConfigData.getPaymentApprovedRequestTopicName(),
                                    paymentApprovedRequestAvroModel,
                                    paymentId,
                                    "PaymentApprovedRequestAvroModel"));

            log.info("PaymentApprovedRequestAvroModel sent to kafka for payment id: {}", paymentId);
        } catch (Exception e) {
            log.error("Error while sending PaymentApprovedRequestAvroModel message" +
                    " to kafka with payment id: {}, error: {}", paymentId, e.getMessage());
        }
    }
}
