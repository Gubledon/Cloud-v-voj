FROM flink:1.19.1-scala_2.12-java17

ENV FLINK_HOME=/opt/flink

# Download Flink SQL connectors for Kafka + Avro (Confluent Schema Registry)
RUN wget -P $FLINK_HOME/lib/ https://repo1.maven.org/maven2/org/apache/flink/flink-sql-connector-kafka/3.2.0-1.19/flink-sql-connector-kafka-3.2.0-1.19.jar && \
    wget -P $FLINK_HOME/lib/ https://repo1.maven.org/maven2/org/apache/flink/flink-sql-avro-confluent-registry/1.19.1/flink-sql-avro-confluent-registry-1.19.1.jar

# Make sql-client.sh easy to invoke
RUN ln -s $FLINK_HOME/bin/sql-client.sh /usr/local/bin/sql-client.sh

WORKDIR /opt/flink
