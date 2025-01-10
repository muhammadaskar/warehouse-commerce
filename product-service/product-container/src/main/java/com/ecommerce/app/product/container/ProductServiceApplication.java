package com.ecommerce.app.product.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.ecommerce.app.product.dataaccess")
@EntityScan(basePackages = { "com.ecommerce.app.product.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.ecommerce.app")
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
