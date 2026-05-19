-- Úloha 1: Fee Service
-- 1% fee from outgoing transactions (amount < 0), rounded to 2 decimals.

CREATE TABLE fees (
  id INT,
  amount DOUBLE,
  fee DOUBLE
) WITH (
    'connector' = 'kafka',
    'topic' = 'fees',
    'properties.bootstrap.servers' = 'broker:9092',
    'key.format' = 'raw',
    'key.fields' = 'id',
    'value.format' = 'avro-confluent',
    'value.avro-confluent.url' = 'http://schema-registry:8081',
    'value.fields-include' = 'EXCEPT_KEY'
);

INSERT INTO fees
SELECT
  id,
  amount,
  ROUND(-amount * 0.01, 2) AS fee
FROM transactions
WHERE amount < 0;
