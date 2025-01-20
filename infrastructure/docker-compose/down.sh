#!/bin/bash

# ANSI color codes
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo -e "${GREEN}Downing Docker Compose...${NC}"

echo -e "${GREEN}Downing Zookeeper...${NC}"
docker compose -f common.yml -f zookeeper.yml down

echo -e "${GREEN}Downing Kafka Cluster...${NC}"
docker compose -f common.yml -f kafka_cluster.yml down

echo -e "${GREEN}Downing Kafka Topic...${NC}"
docker compose -f common.yml -f init_kafka.yml down

echo -e "${GREEN}Downing Postgresql...${NC}"
docker compose -f common.yml -f postgresql.yml down