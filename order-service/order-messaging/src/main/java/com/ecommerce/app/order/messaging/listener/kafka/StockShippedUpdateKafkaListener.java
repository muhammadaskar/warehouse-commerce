package com.ecommerce.app.order.messaging.listener.kafka;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.StockShippedUpdateResponseAvroModel;
import com.ecommerce.app.order.application.service.ports.input.message.listener.warehouse.StockApplicationMessageListener;
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
public class StockShippedUpdateKafkaListener implements KafkaConsumer<StockShippedUpdateResponseAvroModel> {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final StockApplicationMessageListener stockApplicationMessageListener;

    public StockShippedUpdateKafkaListener(OrderMessagingDataMapper orderMessagingDataMapper, StockApplicationMessageListener stockApplicationMessageListener) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.stockApplicationMessageListener = stockApplicationMessageListener;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.stock-shipped-update-consumer-group-id}", topics = "${order-service.stock-shipped-update-topic-name}")
    public void receive(@Payload List<StockShippedUpdateResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {


        messages.forEach(stockShippedUpdateResponseAvroModel -> {
            log.info("Stock shipped update response received: {}", stockShippedUpdateResponseAvroModel);

            stockApplicationMessageListener.updateStockShipped(orderMessagingDataMapper.
                    stockShippedUpdateToStockShippedUpdateAvroModel(stockShippedUpdateResponseAvroModel));
        });
    }
}
