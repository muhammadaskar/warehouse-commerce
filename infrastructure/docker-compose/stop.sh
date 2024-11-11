#!/bin/bash

# ANSI color codes
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo -e "${GREEN}Stop Docker Compose...${NC}"

echo -e "${GREEN}Stop Zookeeper...${NC}"
docker compose -f common.yml -f zookeeper.yml stop

echo -e "${GREEN}Stop Kafka Cluster...${NC}"
docker compose -f common.yml -f kafka_cluster.yml stop