package com.ecommerce.app.user.messaging.listener.kafka;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseResponseAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WarehouseResponseKafkaListener implements KafkaConsumer<WarehouseResponseAvroModel> {

//    private final WarehouseResponseMessageListener warehouseResponseMessageListener;
//    private final UserMessagingDataMapper userMessagingDataMapper;
//
//    public WarehouseResponseKafkaListener(WarehouseResponseMessageListener warehouseResponseMessageListener, UserMessagingDataMapper userMessagingDataMapper) {
//        this.warehouseResponseMessageListener = warehouseResponseMessageListener;
//        this.userMessagingDataMapper = userMessagingDataMapper;
//    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.warehouse-consumer-group-id}", topics = "${user-service.warehouse-request-topic-name}")
    public void receive(@Payload List<WarehouseResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of payment responses received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());
    }
}
