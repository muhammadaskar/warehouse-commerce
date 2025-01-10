package com.ecommerce.app.payment.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.ecommerce.app.payment.dataaccess")
@EntityScan(basePackages = { "com.ecommerce.app.payment.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.ecommerce.app")
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
