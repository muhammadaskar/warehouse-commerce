package com.ecommerce.app.order.messaging.listener.kafka;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.PaymentOrderStatus;
import com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofUploadAvroModel;
import com.ecommerce.app.order.application.service.ports.input.message.listener.payment.PaymentMessageListener;
import com.ecommerce.app.order.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PaymentProofUploadRequestKafkaListener implements KafkaConsumer<PaymentProofUploadAvroModel> {

    private final PaymentMessageListener paymentMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public PaymentProofUploadRequestKafkaListener(PaymentMessageListener paymentMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.paymentMessageListener = paymentMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}", topics = "${order-service.payment-proof-upload-request-topic-name}")
    public void receive(@Payload List<PaymentProofUploadAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.debug("{} number of payment proof upload requests received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(paymentProofUploadAvroModel -> {
            if (paymentProofUploadAvroModel.getPaymentOrderStatus() == PaymentOrderStatus.UNDER_REVIEW) {
                // TODO: Implement the logic to process the payment proof upload request
                log.info("Processing payment proof upload request: {}", paymentProofUploadAvroModel);
                paymentMessageListener.paymentProofUploaded(orderMessagingDataMapper.
                        paymentProofUploadRequestToPaymentProofUploadAvroModel(paymentProofUploadAvroModel));
            }
        });
    }
}
