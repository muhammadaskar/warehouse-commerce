package com.ecommerce.app.order.messaging.listener.kafka;

import com.ecommerce.app.kafka.consumer.KafkaConsumer;
import com.ecommerce.app.kafka.warehouse.avro.model.StockUpdatedRequestAvroModel;
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
public class StockUpdatedKafkaListener implements KafkaConsumer<StockUpdatedRequestAvroModel> {

    private final StockApplicationMessageListener stockApplicationMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public StockUpdatedKafkaListener(StockApplicationMessageListener stockApplicationMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.stockApplicationMessageListener = stockApplicationMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.stock-updated-consumer-group-id}", topics = "${order-service.stock-updated-topic-name}")
    public void receive(@Payload List<StockUpdatedRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        messages.forEach(stockUpdatedRequestAvroModel -> {
            log.info("Stock updated request received: {}", stockUpdatedRequestAvroModel);
            stockApplicationMessageListener.updateStock(orderMessagingDataMapper.
                    stockUpdatedRequestAvroModelToStockUpdated(stockUpdatedRequestAvroModel));
        });
    }
}
