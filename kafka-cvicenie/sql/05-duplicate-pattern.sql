-- Úloha 5: Detekcia rovnakých po sebe idúcich odchádzajúcich transakcií
-- Pomocou MATCH_RECOGNIZE: dve po sebe idúce transakcie z toho istého účtu
-- s rovnakou (zápornou) sumou.

CREATE TABLE duplicate_tx (
  iban VARCHAR,
  amount DOUBLE,
  firstDate TIMESTAMP(3),
  secondDate TIMESTAMP(3)
) WITH (
    'connector' = 'kafka',
    'topic' = 'duplicate_tx',
    'properties.bootstrap.servers' = 'broker:9092',
    'key.format' = 'raw',
    'key.fields' = 'iban',
    'value.format' = 'avro-confluent',
    'value.avro-confluent.url' = 'http://schema-registry:8081',
    'value.fields-include' = 'EXCEPT_KEY'
);

INSERT INTO duplicate_tx
SELECT *
FROM (
  SELECT iban, amount, eventTime
  FROM transactions
  WHERE amount < 0
)
MATCH_RECOGNIZE (
  PARTITION BY iban
  ORDER BY eventTime
  MEASURES
    A.amount      AS amount,
    A.eventTime   AS firstDate,
    B.eventTime   AS secondDate
  PATTERN (A B)
  DEFINE
    B AS B.amount = A.amount
);
