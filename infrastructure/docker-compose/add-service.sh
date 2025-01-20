#!/bin/bash

KONG_ADMIN_API="http://localhost:8001"
WAREHOUSE_SERVICE_NAME="warehouse-service"
USER_SERVICE_NAME="user-service"
WAREHOUSE_SERVICE_URL="http://host.docker.internal:8181/warehouses"
USER_SERVICE_URL="http://host.docker.internal:8282/users"

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
  --data "methods[]=DELETE")
check_response $response "Adding route for Warehouse Service"

echo "Adding rate-limiting plugin for Warehouse Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$WAREHOUSE_SERVICE_NAME/plugins \
  --data "name=rate-limiting" \
  --data "config.second=10" \
  --data "config.policy=local")
check_response $response "Adding rate-limiting plugin for Warehouse Service"

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
  --data "methods[]=DELETE")
check_response $response "Adding route for User Service"

echo "Adding rate-limiting plugin for User Service..."
response=$(curl -s -o /dev/null -w "%{http_code}" -X POST $KONG_ADMIN_API/services/$USER_SERVICE_NAME/plugins \
  --data "name=rate-limiting" \
  --data "config.second=10" \
  --data "config.policy=local")
check_response $response "Adding rate-limiting plugin for User Service"

echo "User Service added successfully."
