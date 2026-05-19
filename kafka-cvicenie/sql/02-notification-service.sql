-- Úloha 2: Notification Service
-- Notify account owner about every transaction with |amount| > 100 EUR.

CREATE TABLE notifications (
  id INT,
  iban VARCHAR,
  email VARCHAR,
  name VARCHAR,
  amount DOUBLE
) WITH (
    'connector' = 'kafka',
    'topic' = 'notifications',
    'properties.bootstrap.servers' = 'broker:9092',
    'key.format' = 'raw',
    'key.fields' = 'id',
    'value.format' = 'avro-confluent',
    'value.avro-confluent.url' = 'http://schema-registry:8081',
    'value.fields-include' = 'EXCEPT_KEY'
);

INSERT INTO notifications
SELECT
  t.id,
  t.iban,
  a.email,
  a.name,
  t.amount
FROM transactions t
JOIN accounts a ON t.iban = a.iban
WHERE ABS(t.amount) > 100;
