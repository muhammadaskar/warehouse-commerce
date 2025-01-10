package com.ecommerce.app.warehouse.messaging.publisher.kafka;

import com.ecommerce.app.warehouse.domain.core.event.StockTransferredEvent;
import com.ecommerce.app.warehouse.domain.service.ports.output.message.publisher.stock.WarehouseStockTransferedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockTransferredKafkaMessagePublisher implements WarehouseStockTransferedMessagePublisher {
    @Override
    public void publish(StockTransferredEvent event) {

    }
}
