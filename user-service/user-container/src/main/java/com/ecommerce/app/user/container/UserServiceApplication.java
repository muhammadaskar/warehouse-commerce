package com.ecommerce.app.user.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories(basePackages = "com.ecommerce.app.user.dataaccess")
//@EntityScan(basePackages = { "com.ecommerce.app.user.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.ecommerce.app")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
