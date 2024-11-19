package com.ecommerce.app.user.messaging.publisher.kafka;

import com.ecommerce.app.kafka.producer.KafkaMessageHelper;
import com.ecommerce.app.kafka.producer.service.KafkaProducer;
import com.ecommerce.app.kafka.warehouse.avro.model.WarehouseAdminCreateAvroModel;
import com.ecommerce.app.user.application.service.config.UserServiceConfigData;
import com.ecommerce.app.user.application.service.ports.output.message.publisher.user.WarehouseAdminCreatedMessagePublisher;
import com.ecommerce.app.user.domain.core.event.WarehouseAdminCreatedEvent;
import com.ecommerce.app.user.messaging.mapper.UserMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarehouseAdminCreatedMessagePublisherImpl implements WarehouseAdminCreatedMessagePublisher {

    private final UserMessagingDataMapper userMessagingDataMapper;
    private final UserServiceConfigData userServiceConfigData;
    private final KafkaProducer<String, WarehouseAdminCreateAvroModel> kafkaProducer;
    private final KafkaMessageHelper userKafkaMessageHelper;

    public WarehouseAdminCreatedMessagePublisherImpl(UserMessagingDataMapper userMessagingDataMapper, UserServiceConfigData userServiceConfigData, KafkaProducer<String, WarehouseAdminCreateAvroModel> kafkaProducer, KafkaMessageHelper userKafkaMessageHelper) {
        this.userMessagingDataMapper = userMessagingDataMapper;
        this.userServiceConfigData = userServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.userKafkaMessageHelper = userKafkaMessageHelper;
    }

    @Override
    public void publish(WarehouseAdminCreatedEvent domainEvent) {
        String userId = domainEvent.getUser().getId().getValue().toString();
        try {
            WarehouseAdminCreateAvroModel warehouseAdminCreateAvroModel = userMessagingDataMapper.
                    warehouseAdminCreatedEventToWarehouseAdminCreateAvroModel(domainEvent);

            kafkaProducer.send(userServiceConfigData.getWarehouseAdminCreateTopicName(),
                    userId,
                    warehouseAdminCreateAvroModel,
                    userKafkaMessageHelper.getKafkaCallback(
                            userServiceConfigData.getWarehouseAdminCreateTopicName(),
                            warehouseAdminCreateAvroModel,
                            userId,
                            "WarehouseAdminCreateAvroModel"
                    ));
            log.info("WarehouseAdminCreateAvroModel has been sent with user id: {}", warehouseAdminCreateAvroModel.getUserId());
        } catch (Exception e) {
            log.error(new StringBuilder()
                    .append("Error while sending WarehouseAdminCreateAvroModel message")
                    .append("to kafka with user id: {}").toString(), userId);
        }
    }
}
