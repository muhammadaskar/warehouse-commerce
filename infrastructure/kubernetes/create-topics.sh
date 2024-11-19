#local-confluent-kafka-cp-zookeeper-headless
kafka-topics --zookeeper $1:2181 --topic warehouse-create-topic --delete --if-exists
kafka-topics --zookeeper $1:2181 --topic warehouse-admin-create-topic --delete --if-exists

kafka-topics --zookeeper $1:2181 --topic warehouse-create-topic --create --partitions 3 --replication-factor 3 --if-not-exists
kafka-topics --zookeeper $1:2181 --topic warehouse-admin-create-topic --create --partitions 3 --replication-factor 3 --if-not-exists