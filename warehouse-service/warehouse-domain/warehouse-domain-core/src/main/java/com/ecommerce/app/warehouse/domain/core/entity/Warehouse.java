package com.ecommerce.app.warehouse.domain.core.entity;

import com.ecommerce.app.common.domain.entity.AggregateRoot;
import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;
import com.ecommerce.app.warehouse.domain.core.exception.WarehouseDomainException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Warehouse extends AggregateRoot<WarehouseId> {
    private String name;
    private Address address;
    private Set<Product> products;
    private List<Stock> stocks;

    public Warehouse(){}

    private Warehouse(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        address = builder.address;
        products = builder.products;
        stocks = builder.stocks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeWarehouse() {
        setId(new WarehouseId(UUID.randomUUID()));
    }

    public int getAvailableStock(ProductId productId) {
        return stocks.stream()
                .filter(stock -> stock.getProductId().equals(productId))
                .filter(stock -> stock.getQuantity() > 0)
                .mapToInt(Stock::getQuantity)
                .sum();
    }

    public int getAvailableStock(ProductId productId, WarehouseId warehouseId) {
        return stocks.stream()
                .filter(stock -> stock.getProductId().equals(productId))
                .filter(stock -> stock.getWarehouseId().equals(warehouseId))
                .mapToInt(Stock::getQuantity)
                .sum();
    }

    public void requestStockToWarehouse(Warehouse warehouse, ProductId productId, int requestedQuantity) {
        Stock stock = warehouse.getStocks().stream()
                .filter(s -> s.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new WarehouseDomainException("Stock not found for product ID: " + productId));

        if (stock.getQuantity() < requestedQuantity) {
            throw new WarehouseDomainException("Insufficient stock for product ID: " + productId.getValue()
                    + " in warehouse ID: " + warehouse.getId().getValue());
        }

        System.out.printf("Stock request processed: Product ID %s, Quantity %d, Warehouse ID %s%n",
                productId.getValue(), requestedQuantity, warehouse.getId().getValue());
    }

    public List<Warehouse> findOtherWarehousesWithStock(List<Warehouse> warehouses, UUID productId, double baseLat, double baseLon) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getAvailableStock(new ProductId(productId)) > 0)
                .sorted(Comparator.comparingDouble(w -> calculateDistance(
                        baseLat,
                        baseLon,
                        Double.parseDouble(w.getAddress().getLatitude()),
                        Double.parseDouble(w.getAddress().getLongitude())
                )))
                .collect(Collectors.toList());
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Convert to kilometers
    }

    public void validateWarehouse() {
        validateName();
        validateAddress();
    }

    private void validateName() {
        if (name == null) {
            throw new WarehouseDomainException("Warehouse name is must be filled");
        }
    }

    private void validateAddress() {
        if (address.getCity().isEmpty() || address.getStreet().isEmpty() || address.getPostalCode().isEmpty()) {
            throw new WarehouseDomainException("Warehouse address must be filled");
        }
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public static final class Builder {
        private WarehouseId id;
        private String name;
        private Address address;
        private Set<Product> products;
        private List<Stock> stocks;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(WarehouseId val) {
            id = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withAddress(Address val) {
            address = val;
            return this;
        }

        public Builder withProducts(Set<Product> val) {
            products = val;
            return this;
        }

        public Builder withStocks(List<Stock> val) {
            stocks = val;
            return this;
        }

        public Warehouse build() {
            return new Warehouse(this);
        }
    }
}
