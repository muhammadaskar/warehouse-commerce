package com.ecommerce.app.order.domain.core.entity;

import com.ecommerce.app.common.domain.entity.BaseEntity;
import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.ProductId;
import com.ecommerce.app.common.domain.valueobject.WarehouseId;

import java.util.List;

public class Warehouse extends BaseEntity<WarehouseId> {
    private final String name;
    private final Address address;
    private List<Stock> stocks;

    private Warehouse(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        address = builder.address;
        stocks = builder.stocks;
    }

    public int getAvailableStock(ProductId productId) {
        return stocks.stream()
                .filter(stock -> stock.getProductId().equals(productId))
                .mapToInt(Stock::getQuantity)
                .sum();
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to kilometers

        return distance;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private WarehouseId id;
        private String name;
        private Address address;
        private List<Product> products;
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

        public Builder withProducts(List<Product> val) {
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
