kubectl exec -it kafka-client sh

kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --list

kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic warehouse-create-topic --delete --if-exists
kafka-topics --zookeeper local-confluent-kafka-cp-zookeeper-headless:2181 --topic warehouse-admin-create-topic --delete --if-exists


kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic warehouse-create-topic --create --partitions 3 --replication-factor 3 --if-not-exists
kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic warehouse-admin-create-topic --create --partitions 3 --replication-factor 3 --if-not-exists