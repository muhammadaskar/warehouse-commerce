    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --list
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic user-create-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic payment-request-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic payment-proof-upload-request-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic payment-proof-upload-response-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic payment-approved-request-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic order-paid-request-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic order-warehouse-response-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic order-processed-request-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic stock-transferred-update-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic order-shipped-request-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic order-shipped-response-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic stock-shipped-update-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic order-confirmed-request-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic stock-updated-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic stock-created-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic warehouse-create-topic --delete --if-exists
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --topic warehouse-admin-create-topic --delete --if-exists
    echo -e 'Successfully deleted kafka topics'

    echo -e 'Creating kafka topics with multiple partitions and higher replication factor'
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic user-create-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic payment-request-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic payment-proof-upload-request-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic payment-proof-upload-response-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic payment-approved-request-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic order-paid-request-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic order-warehouse-response-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic order-processed-request-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic stock-transferred-update-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic order-shipped-request-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic order-shipped-response-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic stock-shipped-update-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic order-confirmed-request-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic stock-updated-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic stock-created-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic warehouse-create-topic --partitions 3 --replication-factor 2
    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --create --topic warehouse-admin-create-topic --partitions 3 --replication-factor 2
    echo -e 'Successfully created the kafka topics'

    kafka-topics --zookeeper gke-confluent-kafka-cp-zookeeper-headless:2181 --list