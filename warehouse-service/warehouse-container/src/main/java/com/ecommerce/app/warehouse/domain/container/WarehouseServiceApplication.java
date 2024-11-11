package com.ecommerce.app.warehouse.domain.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.ecommerce.app.warehouse.dataaccess")
@EntityScan(basePackages = { "com.ecommerce.app.warehouse.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.ecommerce.app")
public class WarehouseServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WarehouseServiceApplication.class, args);
    }
}
