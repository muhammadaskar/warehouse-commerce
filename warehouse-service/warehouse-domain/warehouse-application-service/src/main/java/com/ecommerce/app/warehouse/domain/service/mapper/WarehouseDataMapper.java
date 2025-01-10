package com.ecommerce.app.warehouse.domain.service.mapper;

import com.ecommerce.app.common.domain.valueobject.*;
import com.ecommerce.app.warehouse.domain.core.entity.*;
import com.ecommerce.app.warehouse.domain.core.valueobject.StockJournalId;
import com.ecommerce.app.warehouse.domain.service.dto.create.*;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderPaidRequest;
import com.ecommerce.app.warehouse.domain.service.dto.message.OrderShippedRequest;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class WarehouseDataMapper {

    public Warehouse warehouseCommandToWarehouse(CreateWarehouseCommand command) {
        return Warehouse.builder()
                .withName(command.getName())
                .withAddress(warehouseAddressToStreetAddress(command))
                .build();
    }

    public CreateWarehouseResponse warehouseToCreateWarehouseResponse(Warehouse warehouse, String message) {
        return CreateWarehouseResponse.builder()
                .warehouseId(warehouse.getId().getValue())
                .warehouseName(warehouse.getName())
                .warehouseAddress(warehouse.getAddress())
                .message(message)
                .build();
    }

    private Address warehouseAddressToStreetAddress(CreateWarehouseCommand createWarehouseCommand) {
        return new Address(
                createWarehouseCommand.getStreet(),
                createWarehouseCommand.getPostalCode(),
                createWarehouseCommand.getCity(),
                createWarehouseCommand.getLatitude(),
                createWarehouseCommand.getLongitude()
        );
    }

    public Order orderPaidRequestToOrder(OrderPaidRequest orderPaidRequest) {
        return Order.builder()
                .withId(new OrderId(UUID.fromString(orderPaidRequest.getOrderId())))
                .withWarehouseId(new WarehouseId(UUID.fromString(orderPaidRequest.getWarehouseId())))
                .withOrderStatus(OrderStatus.valueOf(orderPaidRequest.getOrderStatus()))
                .withOrderItems(orderItemsToStocks(orderPaidRequest.getOrderItems()))
                .build();
    }

    public Order orderShippedRequestToOrder(OrderShippedRequest orderShippedRequest) {
        return Order.builder()
                .withId(new OrderId(UUID.fromString(orderShippedRequest.getOrderId())))
                .withWarehouseId(new WarehouseId(UUID.fromString(orderShippedRequest.getWarehouseId())))
                .withOrderStatus(OrderStatus.valueOf(orderShippedRequest.getOrderStatus()))
                .withOrderItems(orderItemsToStocks(orderShippedRequest.getOrderItems()))
                .build();
    }

    private List<OrderItem> orderItemsToStocks(List<com.ecommerce.app.warehouse.domain.service.dto.message.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> OrderItem.builder()
                        .withProductId(new ProductId(UUID.fromString(orderItem.getProductId())))
                        .withQuantity(orderItem.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    public StockJournal stockToStockJournal(Stock stock) {
        return StockJournal.newBuilder()
                .withId(new StockJournalId(UUID.randomUUID()))
                .withProductId(stock.getProductId())
                .withWarehouseId(stock.getWarehouseId())
                .withQuantity(stock.getQuantity())
                .withCreatedAt(ZonedDateTime.now())
                .build();
    }

    public Stock stockRequestCommandToStock(StockRequestCommand stockRequestCommand) {
        return Stock.builder()
                .withWarehouseId(new WarehouseId(stockRequestCommand.getWarehouseId()))
                .withProductId(new ProductId(stockRequestCommand.getProductId()))
                .withQuantity(stockRequestCommand.getQuantity())
                .build();
    }

    public StockRequestedResponse stockToStockRequestedResponse(Stock stock, String message) {
        return StockRequestedResponse.builder()
                .productId(stock.getProductId().getValue())
                .warehouseId(stock.getWarehouseId().getValue())
                .message(message)
                .build();
    }

    public List<WarehouseResponse> warehousesToWarehouseResponses(List<Warehouse> warehouses) {
        return warehouses.stream()
                .map(this::warehouseToWarehouseResponse)
                .collect(Collectors.toList());
    }

    public WarehouseResponse warehouseToWarehouseResponse(Warehouse warehouse) {
        return WarehouseResponse.builder()
                .warehouseId(warehouse.getId().getValue())
                .name(warehouse.getName())
                .address(warehouseAddressToStreetAddress(warehouse))
                .stocks(warehouse.getStocks().stream()
                        .map(stock -> StockResponse.builder()
                                .stockId(stock.getId().getValue())
                                .quantity(stock.getQuantity())
                                .product(ProductResponse.builder()
                                        .productId(stock.getProductId().getValue())
                                        .name(stock.getProduct().getName())
                                        .build())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public List<StockJournalResponse> stockJournalsToStockJournalResponses(List<StockJournal> stockJournals) {
        return stockJournals.stream()
                .map(this::stockJournalToStockJournalResponse)
                .collect(Collectors.toList());
    }

    public StockJournalResponse stockJournalToStockJournalResponse(StockJournal stockJournal) {
        return StockJournalResponse.builder()
                .stockJournalId(stockJournal.getId().getValue())
                .changeType(stockJournal.getChangeType())
                .quantity(stockJournal.getQuantity())
                .reason(stockJournal.getReason())
                .warehouse(WarehouseResponse.builder()
                        .warehouseId(stockJournal.getWarehouseId().getValue())
                        .name(stockJournal.getWarehouse().getName())
                        .build())
                .product(ProductResponse.builder()
                        .productId(stockJournal.getProductId().getValue())
                        .name(stockJournal.getProduct().getName())
                        .build())
                .createdAt(stockJournal.getCreatedAt().toString())
                .build();
    }

    public List<StockTransferResponse> stockTransfersToStockTransferResponses(List<StockTransfer> stockTransfers) {
        return stockTransfers.stream()
                .map(this::stockTransferToStockTransferResponse)
                .collect(Collectors.toList());
    }

    public StockTransferResponse stockTransferToStockTransferResponse(StockTransfer stockTransfer) {
        return StockTransferResponse.builder()
                .stockTransferId(stockTransfer.getId().getValue())
                .quantity(stockTransfer.getQuantity())
                .reason(stockTransfer.getReason())
                .sourceWarehouse(WarehouseIdAndNameResponse.builder()
                        .warehouseId(stockTransfer.getSourceWarehouseId().getValue())
                        .name(stockTransfer.getSourceWarehouse().getName())
                        .build())
                .destinationWarehouse(WarehouseIdAndNameResponse.builder()
                        .warehouseId(stockTransfer.getDestinationWarehouseId().getValue())
                        .name(stockTransfer.getDestinationWarehouse().getName())
                        .build())
                .product(ProductResponse.builder()
                        .productId(stockTransfer.getProductId().getValue())
                        .name(stockTransfer.getProduct().getName())
                        .build())
                .status(stockTransfer.getStatus())
                .createdAt(stockTransfer.getCreatedAt())
                .build();
    }

    private WarehouseAddress warehouseAddressToStreetAddress(Warehouse warehouse) {
        return new WarehouseAddress(
                warehouse.getAddress().getStreet(),
                warehouse.getAddress().getPostalCode(),
                warehouse.getAddress().getCity(),
                warehouse.getAddress().getLatitude(),
                warehouse.getAddress().getLongitude()
        );
    }

    public StockResponse stockToStockResponse(Stock stock) {
        return StockResponse.builder()
                .stockId(stock.getId().getValue())
                .quantity(stock.getQuantity())
                .product(ProductResponse.builder()
                        .productId(stock.getProductId().getValue())
                        .name(stock.getProduct().getName())
                        .build())
                .build();
    }

    public List<StockResponse> stocksToStockResponses(List<Stock> stocks) {
        return stocks.stream()
                .map(this::stockToStockResponse)
                .collect(Collectors.toList());
    }
}
