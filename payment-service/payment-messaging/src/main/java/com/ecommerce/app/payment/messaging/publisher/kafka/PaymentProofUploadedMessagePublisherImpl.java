package com.ecommerce.app.payment.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofUploadAvroModel;
import com.ecommerce.app.payment.application.service.config.PaymentConfigData;
import com.ecommerce.app.payment.application.service.ports.output.message.publisher.payment.PaymentProofUploadedMessagePublisher;
import com.ecommerce.app.payment.domain.core.event.PaymentProofUploadedEvent;
import com.ecommerce.app.payment.messaging.mapper.PaymentMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class PaymentProofUploadedMessagePublisherImpl implements PaymentProofUploadedMessagePublisher {

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final PaymentConfigData paymentConfigData;
    private final KafkaProducer<String, PaymentProofUploadAvroModel> kafkaProducer;
    private final KafkaMessageHelper paymentKafkaMessageHelper;

    public PaymentProofUploadedMessagePublisherImpl(PaymentMessagingDataMapper paymentMessagingDataMapper, PaymentConfigData paymentConfigData, KafkaProducer<String, PaymentProofUploadAvroModel> kafkaProducer, KafkaMessageHelper paymentKafkaMessageHelper) {
        this.paymentMessagingDataMapper = paymentMessagingDataMapper;
        this.paymentConfigData = paymentConfigData;
        this.kafkaProducer = kafkaProducer;
        this.paymentKafkaMessageHelper = paymentKafkaMessageHelper;
    }

    @Override
    public void publish(PaymentProofUploadedEvent event) {
        String paymentId = event.getPayment().getId().getValue().toString();

        try {
            PaymentProofUploadAvroModel paymentProofUploadAvroModel =
                    paymentMessagingDataMapper.paymentProofUploadEventToPaymentProofUploadAvroModel(event);


            kafkaProducer.send(paymentConfigData.getPaymentProofUploadRequestTopicName(),
                    paymentId,
                    paymentProofUploadAvroModel,
                    paymentKafkaMessageHelper
                            .getKafkaCallback(paymentConfigData.getPaymentProofUploadRequestTopicName(),
                                    paymentProofUploadAvroModel,
                                    paymentId,
                                    "PaymentProofUploadAvroModel"));

            log.info("PaymentProofUploadAvroModel sent to kafka for payment id: {}", paymentId);
        } catch (Exception e) {
            log.error("Error while sending PaymentProofUploadAvroModel message" +
                    " to kafka with payment id: {}, error: {}", paymentId, e.getMessage());
        }
    }
}
