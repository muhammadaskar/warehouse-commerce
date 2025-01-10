package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.OrderId;
import com.ecommerce.app.common.domain.valueobject.OrderStatus;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Order extends BaseEntity<OrderId> {
    private final WarehouseId warehouseId;
    private OrderStatus orderStatus;
    private final List<OrderItem> orderItems;
    private final List<StockTransfer> stockTransfers = new ArrayList<>();
    private final List<Stock> stocks = new ArrayList<>();

    public void validateStockBeforeShipped(Warehouse currentWarehouse) {
        try {
            for (OrderItem item : orderItems) {
                int quantity = item.getQuantity();
                int availableStockInCurrentWarehouse = currentWarehouse.getAvailableStock(item.getProductId());

                if (availableStockInCurrentWarehouse < quantity) {
                    throw new WarehouseDomainException(
                            "Insufficient stock for product ID " + item.getProductId().getValue() +
                                    " in warehouse ID " + currentWarehouse.getId().getValue()
                    );
                }
                stocks.add(new Stock(currentWarehouse.getId(), item.getProductId(), quantity));
            }
            this.orderStatus = OrderStatus.SHIPPED;
        } catch (WarehouseDomainException e) {
            this.orderStatus = OrderStatus.CANCELLED;
            throw e;
        }
    }

    public void validateStockInWarehouses(List<Warehouse> warehouses) {
        Warehouse currentWarehouse = getCurrentWarehouse(warehouses);

        try {
            for (OrderItem item : orderItems) {
                int remainingQuantity = item.getQuantity();
                int availableStockInCurrentWarehouse = currentWarehouse.getAvailableStock(item.getProductId());

                if (availableStockInCurrentWarehouse >= remainingQuantity) {
                    continue;
                }
                remainingQuantity -= availableStockInCurrentWarehouse;
                while (remainingQuantity > 0) {
                    Warehouse nearestWarehouse = findNearestWarehouseWithPartialOrFullStock(warehouses, item);

                    if (nearestWarehouse == null) {
                        throw new WarehouseDomainException(
                                "Insufficient stock for product ID " + item.getProductId().getValue() +
                                        " across all warehouses."
                        );
                    }

                    int availableStockInNearestWarehouse = nearestWarehouse.getAvailableStock(item.getProductId());
                    int stockToRequest = Math.min(remainingQuantity, availableStockInNearestWarehouse);
                    nearestWarehouse.requestStockToWarehouse(nearestWarehouse, item.getProductId(), stockToRequest);
                    remainingQuantity -= stockToRequest;
                    stockTransfers.add(new StockTransfer(nearestWarehouse.getId(), currentWarehouse.getId(), item.getProductId(), stockToRequest));
                }
            }
            this.orderStatus = OrderStatus.PROCESSED;
        } catch (WarehouseDomainException e) {
            this.orderStatus = OrderStatus.CANCELLED;
            throw e;
        }
    }

    public List<StockTransfer> getStockTransfers() {
        return stockTransfers;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    private Warehouse findNearestWarehouseWithPartialOrFullStock(List<Warehouse> warehouses, OrderItem item) {
        if (warehouses == null || warehouses.isEmpty()) {
            throw new WarehouseDomainException("Warehouse list cannot be null or empty.");
        }

        if (item == null) {
            throw new WarehouseDomainException("Order item cannot be null.");
        }

        Warehouse selectedWarehouse = null;
        double minDistance = Double.MAX_VALUE;

        Warehouse currentWarehouse = getCurrentWarehouse(warehouses);
        double currentLat = Double.parseDouble(currentWarehouse.getAddress().getLatitude());
        double currentLon = Double.parseDouble(currentWarehouse.getAddress().getLongitude());

        for (Warehouse warehouse : warehouses) {
            if (!warehouse.getId().equals(currentWarehouse.getId())) {
                double distance = currentWarehouse.calculateDistance(
                        currentLat, currentLon,
                        Double.parseDouble(warehouse.getAddress().getLatitude()),
                        Double.parseDouble(warehouse.getAddress().getLongitude())
                );

                if (distance < minDistance && warehouse.getAvailableStock(item.getProductId(), warehouseId) > 0) {
                    minDistance = distance;
                    selectedWarehouse = warehouse;
                }
            }
        }

        if (selectedWarehouse == null) {
            List<Warehouse> backupWarehouses = currentWarehouse.findOtherWarehousesWithStock(
                    warehouses,
                    item.getProductId().getValue(),
                    Double.parseDouble(currentWarehouse.getAddress().getLatitude()),
                    Double.parseDouble(currentWarehouse.getAddress().getLongitude())
            );

            for (Warehouse warehouse : backupWarehouses) {
                if (!warehouse.getId().equals(currentWarehouse.getId())) {
                    double distance = warehouse.calculateDistance(
                            currentLat, currentLon,
                            Double.parseDouble(warehouse.getAddress().getLatitude()),
                            Double.parseDouble(warehouse.getAddress().getLongitude())
                    );

                    if (distance < minDistance) {
                        minDistance = distance;
                        selectedWarehouse = warehouse;
                    }
                }
            }
        }

        int totalStock = currentWarehouse.getAvailableStock(item.getProductId(), warehouseId);
        if (selectedWarehouse != null) {
            totalStock += selectedWarehouse.getAvailableStock(item.getProductId(), selectedWarehouse.getId());
        }

        if (totalStock < item.getQuantity()) {
            throw new WarehouseDomainException("No warehouse found with sufficient stock for product ID: " + item.getProductId().getValue());
        }

        return selectedWarehouse;
    }

    private Warehouse getCurrentWarehouse(List<Warehouse> warehouses) {
        return warehouses.stream()
                .filter(w -> w.getId().equals(warehouseId))
                .findFirst()
                .orElseThrow(() -> new WarehouseDomainException("Invalid warehouse ID: " + warehouseId.getValue()));
    }

    private Order(Builder builder) {
        super.setId(builder.id);
        warehouseId = builder.warehouseId;
        orderStatus = builder.orderStatus;
        orderItems = builder.orderItems;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderId id;
        private WarehouseId warehouseId;
        private OrderStatus orderStatus;
        private List<OrderItem> orderItems;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(OrderId val) {
            id = val;
            return this;
        }

        public Builder withWarehouseId(WarehouseId val) {
            warehouseId = val;
            return this;
        }

        public Builder withOrderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder withOrderItems(List<OrderItem> val) {
            orderItems = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
