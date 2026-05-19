-- Úloha 4: Prekročenie počtu transakcií (> 3 za 5 hodín)
-- Hopping window, size 5 hours, slide 1 hour.

CREATE TABLE tx_count_limit (
  iban VARCHAR,
  tx_count BIGINT,
  window_start TIMESTAMP(3),
  window_end TIMESTAMP(3)
) WITH (
    'connector' = 'kafka',
    'topic' = 'tx_count_limit',
    'properties.bootstrap.servers' = 'broker:9092',
    'key.format' = 'raw',
    'key.fields' = 'iban',
    'value.format' = 'avro-confluent',
    'value.avro-confluent.url' = 'http://schema-registry:8081',
    'value.fields-include' = 'EXCEPT_KEY'
);

INSERT INTO tx_count_limit
SELECT
  iban,
  COUNT(*) AS tx_count,
  window_start,
  window_end
FROM TABLE(
  HOP(TABLE transactions, DESCRIPTOR(eventTime), INTERVAL '1' HOUR, INTERVAL '5' HOUR)
)
GROUP BY iban, window_start, window_end
HAVING COUNT(*) > 3;
