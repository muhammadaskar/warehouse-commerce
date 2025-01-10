package com.ecommerce.app.order.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.ecommerce.app.order.dataaccess")
@EntityScan(basePackages = { "com.ecommerce.app.order.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.ecommerce.app")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
