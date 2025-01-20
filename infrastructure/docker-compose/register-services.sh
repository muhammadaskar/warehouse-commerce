#!/bin/bash

KONG_ADMIN_API="http://localhost:8001"
WAREHOUSE_SERVICE_NAME="warehouse-service"
USER_SERVICE_NAME="user-service"
PRODUCT_SERVICE_NAME="product-service"
ORDER_SERVICE_NAME="order-service"
PAYMENT_SERVICE_NAME="payment-service"
WAREHOUSE_SERVICE_URL="http://warehouse-service:8181/warehouses"
USER_SERVICE_URL="http://user-service:8282/users"
PRODUCT_SERVICE_URL="http://product-service:8383/products"
ORDER_SERVICE_URL="http://order-service:8484/orders"
PAYMENT_SERVICE_URL="http://payment-service:8585/payments"

# Function to check response
check_response() {
  local status=$1
  local action=$2
  if [[ $status -ge 200 && $status -lt 300 ]]; then
    echo "[SUCCESS] $action succeeded."
  else
    echo "[FAILED] $action failed. Check Kong configuration and logs."
    exit 1
  fi
}

echo "Adding Warehouse Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services \
  --data "name=$WAREHOUSE_SERVICE_NAME" \
  --data-urlencode "url=$WAREHOUSE_SERVICE_URL")
check_response $response "Adding Warehouse Service"

echo "Adding route for Warehouse Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$WAREHOUSE_SERVICE_NAME/routes \
  --data "paths[]=/warehouses" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE" \
  --data "methods[]=OPTIONS")
check_response $response "Adding route for Warehouse Service"

echo "Adding rate-limiting plugin for Warehouse Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$WAREHOUSE_SERVICE_NAME/plugins \
  --data "name=rate-limiting" \
  --data "config.second=10" \
  --data "config.policy=local")
check_response $response "Adding rate-limiting plugin for Warehouse Service"

echo "Adding cors plugin for Warehouse Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$WAREHOUSE_SERVICE_NAME/plugins \
  --data "name=cors" \
  --data "config.methods[]=GET" \
  --data "config.methods[]=POST" \
  --data "config.methods[]=PUT" \
  --data "config.methods[]=DELETE" \
  --data "config.methods[]=OPTIONS" \
  --data "config.headers=*" \
  --data "config.origins=*" \
  --data "config.exposed_headers=*" \
  --data "enabled=true" \
  --data "config.credentials=true")
check_response $response "Adding cors plugin for Warehouse Service"
echo "Warehouse Service added successfully."

echo "Adding User Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services \
  --data "name=$USER_SERVICE_NAME" \
  --data-urlencode "url=$USER_SERVICE_URL")
check_response $response "Adding User Service"

echo "Adding route for User Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$USER_SERVICE_NAME/routes \
  --data "paths[]=/users" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE" \
  --data "methods[]=OPTIONS")
check_response $response "Adding route for User Service"

echo "Adding rate-limiting plugin for User Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$USER_SERVICE_NAME/plugins \
  --data "name=rate-limiting" \
  --data "config.second=10" \
  --data "config.policy=local")
check_response $response "Adding rate-limiting plugin for User Service"
echo "User Service added successfully."

echo "Adding cors plugin for User Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$USER_SERVICE_NAME/plugins \
  --data "name=cors" \
  --data "config.methods[]=GET" \
  --data "config.methods[]=POST" \
  --data "config.methods[]=PUT" \
  --data "config.methods[]=DELETE" \
  --data "config.methods[]=OPTIONS" \
  --data "config.headers=*" \
  --data "config.origins=*" \
  --data "config.exposed_headers=*" \
  --data "enabled=true" \
  --data "config.credentials=true")
check_response $response "Adding cors plugin for User Service"
echo "User Service added successfully."

echo "Adding Product Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services \
  --data "name=$PRODUCT_SERVICE_NAME" \
  --data-urlencode "url=$PRODUCT_SERVICE_URL")
check_response $response "Adding Product Service"

echo "Adding route for Product Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$PRODUCT_SERVICE_NAME/routes \
  --data "paths[]=/products" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE" \
  --data "methods[]=OPTIONS")
check_response $response "Adding route for Product Service"

echo "Adding rate-limiting plugin for Product Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$PRODUCT_SERVICE_NAME/plugins \
  --data "name=rate-limiting" \
  --data "config.second=10" \
  --data "config.policy=local")
check_response $response "Adding rate-limiting plugin for Product Service"

echo "Adding cors plugin for Product Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$PRODUCT_SERVICE_NAME/plugins \
  --data "name=cors" \
  --data "config.methods[]=GET" \
  --data "config.methods[]=POST" \
  --data "config.methods[]=PUT" \
  --data "config.methods[]=DELETE" \
  --data "config.methods[]=OPTIONS" \
  --data "config.headers=*" \
  --data "config.origins=*" \
  --data "config.exposed_headers=*" \
  --data "enabled=true" \
  --data "config.credentials=true")
check_response $response "Adding cors plugin for Product Service"
echo "Product Service added successfully."

echo "Adding Order Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services \
  --data "name=$ORDER_SERVICE_NAME" \
  --data-urlencode "url=$ORDER_SERVICE_URL")
check_response $response "Adding Order Service"

echo "Adding route for Order Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$ORDER_SERVICE_NAME/routes \
  --data "paths[]=/orders" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE" \
  --data "methods[]=OPTIONS")
check_response $response "Adding route for Order Service"

echo "Adding rate-limiting plugin for Order Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$ORDER_SERVICE_NAME/plugins \
  --data "name=rate-limiting" \
  --data "config.second=10" \
  --data "config.policy=local")
check_response $response "Adding rate-limiting plugin for Order Service"

echo "Adding cors plugin for Order Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$ORDER_SERVICE_NAME/plugins \
  --data "name=cors" \
  --data "config.methods[]=GET" \
  --data "config.methods[]=POST" \
  --data "config.methods[]=PUT" \
  --data "config.methods[]=DELETE" \
  --data "config.methods[]=OPTIONS" \
  --data "config.headers=*" \
  --data "config.origins=*" \
  --data "config.exposed_headers=*" \
  --data "enabled=true" \
  --data "config.credentials=true")
check_response $response "Adding cors plugin for Order Service"
echo "Order Service added successfully."

echo "Adding Payment Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services \
  --data "name=$PAYMENT_SERVICE_NAME" \
  --data-urlencode "url=$PAYMENT_SERVICE_URL")
check_response $response "Adding Payment Service"

echo "Adding route for Payment Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$PAYMENT_SERVICE_NAME/routes \
  --data "paths[]=/payments" \
  --data "methods[]=GET" \
  --data "methods[]=POST" \
  --data "methods[]=PUT" \
  --data "methods[]=DELETE" \
  --data "methods[]=OPTIONS")
check_response $response "Adding route for Payment Service"

echo "Adding rate-limiting plugin for Payment Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$PAYMENT_SERVICE_NAME/plugins \
  --data "name=rate-limiting" \
  --data "config.second=10" \
  --data "config.policy=local")
check_response $response "Adding rate-limiting plugin for Payment Service"

echo "Adding cors plugin for Payment Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$PAYMENT_SERVICE_NAME/plugins \
  --data "name=cors" \
  --data "config.methods[]=GET" \
  --data "config.methods[]=POST" \
  --data "config.methods[]=PUT" \
  --data "config.methods[]=DELETE" \
  --data "config.methods[]=OPTIONS" \
  --data "config.headers=*" \
  --data "config.origins=*" \
  --data "config.exposed_headers=*" \
  --data "enabled=true" \
  --data "config.credentials=true")
check_response $response "Adding cors plugin for Payment Service"
echo "Payment Service added successfully."
