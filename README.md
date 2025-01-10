# Multi Warehouse Commerce System

## Description
This is multi warehouse commerce system. It is a simple system that allows you to manage products and warehouses. You can add, update, delete products and warehouses. You can also transfer products between warehouses.

## Deployment YAML
link to the deployment directory: [Deployment Directory](https://github.com/muhammadaskar/warehouse-commerce/tree/main/infrastructure/kubernetes)

## Schema Design
link to the schema design: [Schema Design](https://drive.google.com/drive/folders/1Kzaj3ETTT-ioH42vlp80YNX3Xz6mVOvi)

## Documentation
### High Level Design
link to the high level design: [High Level Design](https://drive.google.com/file/d/18we8XNhFTgDHbGVsd_wtF86V4QhJeNM9/view?usp=sharing)

### Domain Driven Design
link to the domain driven design: [Domain Driven Design](https://drive.google.com/file/d/18we8XNhFTgDHbGVsd_wtF86V4QhJeNM9/view?usp=sharing)

### Service Responsibility
- Warehouse service: responsible for managing the warehouse, stock in the warehouse, and transfer stock between warehouses.
- User service: responsible for managing the user, user address.
- Product service: responsible for managing the product, product category, and product stock.
- Order service: responsible for managing the order, order detail, and order status.
- Payment service: responsible for managing the payment, payment status, and payment method.

### Instructions setup
### Requirements
- Java 17
- Maven
- Postgresql
- Docker
- Intellij or Eclipse

### How to run
1. Clone the repository
2. Go to the project directory
3. Go to the infrastructure directory and docker-compose directory
4. Run the following command to start the database
```
docker compose -f postgresql.yml up -d
```
5. Run the following command to start the zookeeper
```
docker compose -f .\common.yml -f .\zookeeper.yml up -d
```
6. After run zookeeper, run the kafka cluster
```
docker compose -f .\common.yml -f .\kafka_cluster.yml up -d
```
7. Init the kafka topic
```
docker compose -f .\common.yml -f .\init_kafka.yml up
```
8. Go back to the project directory
9. Run the maven command to build the project
```
mvn clean install
```
10. Go to the container directory in service module using intellij or eclipse
11. Run the main class in the container directory all the services
12. Link to the services
- Warehouse service: http://localhost:8181
- User service: http://localhost:8282
- Product service: http://localhost:8383
- Order service: http://localhost:8484
- Payment service: http://localhost:8585


13. If the services are running, you can choose accounts to login
- Customer: 
```json
{
    "email": "customer@gmail.com",
    "password": "askar123"
}
```

- Warehouse Admin:
```json
{
 "email": "warehouseadmin1@gmail.com",
 "password": "askar123"
}
```

- Warehouse Admin:
```json
{
 "email": "warehouseadmin2@gmail.com",
 "password": "askar123"
}
```

- Warehouse Admin:
```json
{
 "email": "superadmin@gmail.com",
 "password": "askar123"
}
```

14. Link postman collection: [Postman Collection](https://drive.google.com/drive/folders/1mzEKNvdjkSUmAAWGBR3Ie6wIw3V4X_sb)

### Test Results
- Warehouse service
```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.396 s - in com.ecommerce.app.warehouse.application.service.WarehouseApplicationServiceTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.790 s
[INFO] Finished at: 2025-01-10T01:40:00+07:00
[INFO] ------------------------------------------------------------------------
```

- User service
```
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.921 s - in com.ecommerce.app.user.application.service.UserApplicationServiceTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.912 s
[INFO] Finished at: 2025-01-10T01:38:00+07:00
[INFO] ------------------------------------------------------------------------
```

- Order service
```
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.369 s - in com.ecommerce.app.order.application.service.OrderApplicationServiceTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.659 s
[INFO] Finished at: 2025-01-10T01:41:28+07:00
[INFO] ------------------------------------------------------------------------
```
- Payment service
```
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.012 s - in com.ecommerce.app.payment.application.service.PaymentApplicationServiceTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.785 s
[INFO] Finished at: 2025-01-10T01:42:40+07:00
[INFO] ------------------------------------------------------------------------
```




