package com.ecommerce.app.payment.messaging.listener.kafka;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus;
import com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel;
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
public class PaymentProofResponseKafkaListener implements KafkaConsumer<PaymentProofResponseAvroModel> {

    private final OrderApplicationMessageListener orderApplicationMessageListener;
    private final PaymentMessagingDataMapper paymentMessagingDataMapper;

    public PaymentProofResponseKafkaListener(OrderApplicationMessageListener orderApplicationMessageListener, PaymentMessagingDataMapper paymentMessagingDataMapper) {
        this.orderApplicationMessageListener = orderApplicationMessageListener;
        this.paymentMessagingDataMapper = paymentMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-pending-consumer-group-id}", topics = "${payment-service.payment-proof-upload-response-topic-name}")
    public void receive(@Payload List<PaymentProofResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        messages.forEach(paymentProofResponseAvroModel -> {
            log.info("Received payment proof response for order with id: {}", paymentProofResponseAvroModel.getOrderId());
            if (paymentProofResponseAvroModel.getOrderStatus() == OrderStatus.PENDING) {
                orderApplicationMessageListener.updateOrderStatusToPending(paymentMessagingDataMapper.
                        paymentProofResponseAvroModelToPaymentProofResponse(paymentProofResponseAvroModel));
            }
        });
    }
}
