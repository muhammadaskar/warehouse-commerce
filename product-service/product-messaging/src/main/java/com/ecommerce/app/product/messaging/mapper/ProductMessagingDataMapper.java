package com.ecommerce.app.product.messaging.mapper;

import com.ecommerce.app.kafka.warehouse.avro.model.ProductCreatedRequestAvroModel;
import com.ecommerce.app.product.domain.core.entity.Product;
import com.ecommerce.app.product.domain.core.event.ProductCreatedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ProductMessagingDataMapper {

    public ProductCreatedRequestAvroModel productCreatedEventToProductCreatedAvroModel(ProductCreatedEvent productCreatedEvent) {
        Product product = productCreatedEvent.getProduct();
        ZonedDateTime createdAt = ZonedDateTime.parse(productCreatedEvent.getCreatedAt().toString(), DateTimeFormatter.ISO_DATE_TIME);
        return ProductCreatedRequestAvroModel.newBuilder()
                .setId(product.getId().getValue())
                .setSku(product.getSku())
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setPrice(BigDecimal.valueOf(product.getPrice()))
                .setImageUrl(product.getImageUrl())
                .setCreatedAt(createdAt.toInstant())
                .build();
    }
}
