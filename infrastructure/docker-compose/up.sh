#!/bin/bash

# ANSI color codes
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo -e "${GREEN}Up Docker Compose...${NC}"

echo -e "${GREEN}Up Zookeeper...${NC}"
docker compose -f common.yml -f zookeeper.yml up -d

echo -e "${GREEN}Up Kafka Cluster...${NC}"
docker compose -f common.yml -f kafka_cluster.yml up -d

echo -e "${GREEN}Up Kafka Topic...${NC}"
docker compose -f common.yml -f init_kafka.yml up

echo -e "${GREEN}Up Postgresql...${NC}"
docker compose -f common.yml -f postgresql.yml up -d