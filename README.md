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

### Dependency Diagram
<img src="https://github.com/muhammadaskar/warehouse-commerce/blob/development/target/dependency-graph.png" width="1000" height="200">

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
```
git clone https://github.com/muhammadaskar/warehouse-commerce.git
```
#### Using Docker Compose
2. Go to the project directory
3. Go to the infrastructure directory and docker-compose directory
4. Run the following command to start the database
```
docker compose -f .\common.yml -f .\postgresql.yml up -d
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
8. Run services using docker compose
```
docker compose -f .\common.yml -f .\docker-compose.yml up -d
```
9. If any service failed to start, you can rebuild the service using the following command
```
docker compose -f .\common.yml -f .\docker-compose.yml up -d --build <service-name>
```

10. If you want to see the logs of the services, you can use the following command
```
docker compose -f .\common.yml -f .\docker-compose.yml logs -f <service-name>
```
#### Using Java and Maven
11. Make sure you have installed postgresql in your machine, you can use docker to run the postgresql
12. Make sure you have installed zookeeper and kafka in your machine, you can use docker to run the zookeeper and kafka
13. Run services using maven, go to the project directory and run the following command. Make sure you have java 17 and maven installed in your machine.
```
mvn clean install
```
14. Go to the container directory in service module using intellij or eclipse
15. Run the main class in the container directory all the services

#### Using Kubernetes
16. Run using **kubernetes**, make sure you already installed `minikube` or enable `kubernetes engine` in docker desktop, go to the infrastructure directory and kubernetes directory
17. Go to the `helm` directory and run the following command to install the services
```
git clone https://github.com/confluentinc/cp-helm-charts
```

18. Go to the cp-helm-charts directory and find `poddisruptionbudget.yaml` in the `/charts/cp-zookeeper/templates/` directory and change the `apiVersion` to `policy/v1`
19. Get back to the helm directory and run the following command to install kafka
```
helm install gke-confluent-kafka cp-helm-charts --version 0.6.0
```
20. Get back to the kubernetes directory and run the following command to install the services kafka
```
kubectl apply -f kafka-client.yml.
```

```
kubectl cp create-topics.sh kafka-client:/kafka-client-storage
```

```
kubectl exec -it kafka-client sh
```

```
cd ../../kafka-client-storage
```

```
sh create-topics.sh gke-confluent-kafka-zookeeper-headless
```
21. Run the following command to exec the postgresql
```
kubectl apply -f postgresql.yml
```

22. Run the following command to apply the services
```
kubectl apply -f application-deployment-local.yml
```

23. Make sure all the services are running
```
kubectl get pods
```

24. Link to the services
- Warehouse service: http://localhost:8181
- User service: http://localhost:8282
- Product service: http://localhost:8383
- Order service: http://localhost:8484
- Payment service: http://localhost:8585

25. If the services are running, you can choose accounts to login
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

26. Link postman collection: [Postman Collection](https://drive.google.com/drive/folders/1mzEKNvdjkSUmAAWGBR3Ie6wIw3V4X_sb)

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




