#!/bin/bash

# Load environment variables from .env file
if [ -f .env ]; then
  export $(cat .env | xargs)
else
  echo ".env file not found!"
  exit 1
fi

# Check if KONG_ADMIN_URL is set
if [ -z "$KONG_ADMIN_URL" ]; then
  echo "KONG_ADMIN_URL is not set in .env file!"
  exit 1
fi

# Warehouse Service
curl -i -X POST "$KONG_ADMIN_URL/services" \
  --data "name=warehouse-service" \
  --data "url=http://warehouse-service.default.svc.cluster.local:8181/warehouses"

curl -i -X POST "$KONG_ADMIN_URL/services/warehouse-service/routes" \
  --data "paths[]=/warehouses" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE"

curl -i -X POST "$KONG_ADMIN_URL/services/warehouse-service/plugins" \
  --data "name=rate-limiting" \
  --data "config.second=5" \
  --data "config.policy=local"

# User Service
curl -i -X POST "$KONG_ADMIN_URL/services" \
  --data "name=user-service" \
  --data "url=http://user-service.default.svc.cluster.local:8282/users"

curl -i -X POST "$KONG_ADMIN_URL/services/user-service/routes" \
  --data "paths[]=/users" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE"

curl -i -X POST "$KONG_ADMIN_URL/services/user-service/plugins" \
  --data "name=rate-limiting" \
  --data "config.second=5" \
  --data "config.policy=local"

# Product Service
curl -i -X POST "$KONG_ADMIN_URL/services" \
  --data "name=product-service" \
  --data "url=http://product-service.default.svc.cluster.local:8383/products"

curl -i -X POST "$KONG_ADMIN_URL/services/product-service/routes" \
  --data "paths[]=/products" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE"

curl -i -X POST "$KONG_ADMIN_URL/services/product-service/plugins" \
  --data "name=rate-limiting" \
  --data "config.second=5" \
  --data "config.policy=local"


# Order Service
curl -i -X POST "$KONG_ADMIN_URL/services" \
  --data "name=order-service" \
  --data "url=http://order-service.default.svc.cluster.local:8484/orders"

curl -i -X POST "$KONG_ADMIN_URL/services/order-service/routes" \
  --data "paths[]=/orders" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE"

curl -i -X POST "$KONG_ADMIN_URL/services/order-service/plugins" \
  --data "name=rate-limiting" \
  --data "config.second=5" \
  --data "config.policy=local"

# Payment Service
curl -i -X POST "$KONG_ADMIN_URL/services" \
  --data "name=payment-service" \
  --data "url=http://payment-service.default.svc.cluster.local:8585/payments"

curl -i -X POST "$KONG_ADMIN_URL/services/payment-service/routes" \
  --data "paths[]=/payments" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE"

curl -i -X POST "$KONG_ADMIN_URL/services/payment-service/plugins" \
  --data "name=rate-limiting" \
  --data "config.second=5" \
  --data "config.policy=local"

# Clear Kong cache
curl -X PURGE "$KONG_ADMIN_URL/cache"