-- Úloha 3: Prekročenie denného limitu odchádzajúcich transakcií (> 400 EUR/deň)
-- Tumbling 1-day window over event time, only outgoing (amount < 0).
-- Outflow vyjadrený ako kladné číslo.

CREATE TABLE aml_daily_limit (
  iban VARCHAR,
  outflow DOUBLE,
  day_date DATE
) WITH (
    'connector' = 'kafka',
    'topic' = 'aml_daily_limit',
    'properties.bootstrap.servers' = 'broker:9092',
    'key.format' = 'raw',
    'key.fields' = 'iban',
    'value.format' = 'avro-confluent',
    'value.avro-confluent.url' = 'http://schema-registry:8081',
    'value.fields-include' = 'EXCEPT_KEY'
);

INSERT INTO aml_daily_limit
SELECT
  iban,
  -SUM(amount) AS outflow,
  CAST(window_start AS DATE) AS day_date
FROM TABLE(
  TUMBLE(TABLE transactions, DESCRIPTOR(eventTime), INTERVAL '1' DAY)
)
WHERE amount < 0
GROUP BY iban, window_start, window_end
HAVING -SUM(amount) > 400;
