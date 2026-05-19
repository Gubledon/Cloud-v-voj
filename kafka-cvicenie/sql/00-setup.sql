-- Initial setup: source tables and sample data

SET 'sql-client.execution.result-mode' = 'tableau';

CREATE TABLE transactions (
  id INT,
  description VARCHAR,
  amount DOUBLE,
  iban VARCHAR,
  counterpartyIban VARCHAR,
  executionDate VARCHAR,
  currency VARCHAR,
  eventTime AS TO_TIMESTAMP(executionDate),
  WATERMARK FOR eventTime AS eventTime
) WITH (
    'connector' = 'kafka',
    'topic' = 'transactions',
    'properties.bootstrap.servers' = 'broker:9092',
    'scan.startup.mode' = 'earliest-offset',
    'key.format' = 'raw',
    'key.fields' = 'id',
    'value.format' = 'avro-confluent',
    'value.avro-confluent.url' = 'http://schema-registry:8081',
    'value.fields-include' = 'EXCEPT_KEY'
);

CREATE TABLE accounts (
  iban VARCHAR,
  name VARCHAR,
  email VARCHAR
) WITH (
    'connector' = 'kafka',
    'topic' = 'accounts',
    'properties.bootstrap.servers' = 'broker:9092',
    'scan.startup.mode' = 'earliest-offset',
    'key.format' = 'raw',
    'key.fields' = 'iban',
    'value.format' = 'avro-confluent',
    'value.avro-confluent.url' = 'http://schema-registry:8081',
    'value.fields-include' = 'ALL'
);

-- Sample transactions (basic set)
INSERT INTO transactions (id, description, amount, iban, counterpartyIban, executionDate, currency) VALUES
  (1, 'Payment for groceries', -55.20, 'SK3111000000002923456789', 'GB29NWBK60161331926819', '2026-04-01 09:00:00', 'EUR'),
  (2, 'Payment for rent', -800.00, 'SK3111000000002923456789', 'IT60X0542811101000000123456', '2026-04-02 10:00:00', 'EUR'),
  (3, 'Salary', 2000.00, 'FR7618206000572476985163', 'CH9300762011623852957', '2026-04-03 11:00:00', 'EUR'),
  (4, 'Payment for internet', -40.00, 'DE6508000000192000145399', 'ES9121000418450200051332', '2026-04-04 12:00:00', 'EUR'),
  (5, 'Payment for utilities', -120.00, 'SK3111000000002923456789', 'AT611904300234573201', '2026-04-05 13:00:00', 'EUR'),
  (6, 'Payment for restaurant', -35.00, 'NL02ABNA0457893456', 'BE68539007547034', '2026-04-06 14:00:00', 'EUR'),
  (7, 'Payment for gas', -50.00, 'DE6508000000192000145399', 'PT50000201231234567890154', '2026-04-07 15:00:00', 'EUR'),
  (8, 'Payment for car insurance', -300.00, 'SK3111000000002923456789', 'CY17002001280000001200527600', '2026-04-08 16:00:00', 'EUR'),
  (9, 'Payment for cinema', -10.00, 'FR7618206000572476985163', 'GB82WEST12345698765432', '2026-04-09 17:00:00', 'EUR'),
  (10, 'Payment for groceries', -70.00, 'DE6508000000192000145399', 'GB29NWBK60161331926819', '2026-04-10 18:00:00', 'EUR');

-- Additional transactions for tasks 3, 4, 5
INSERT INTO transactions (id, description, amount, iban, counterpartyIban, executionDate, currency) VALUES
  (11, 'Payment for phone bill', -300.00, 'SK3111000000002923456789', 'AT022050302101023600', '2026-04-11 19:00:00', 'EUR'),
  (12, 'Payment for haircut', -20.00, 'DE6508000000192000145399', 'DK5000400440116243', '2026-04-13 22:00:00', 'EUR'),
  (13, 'Payment for groceries', -260.00, 'DE6508000000192000145399', 'GB29NWBK60161331926819', '2026-04-13 23:00:00', 'EUR'),
  (14, 'Payment for gas', -300.00, 'DE6508000000192000145399', 'PT50000201231234567890154', '2026-04-14 01:00:00', 'EUR'),
  (15, 'Payment for coffee', -5.00, 'DE6508000000192000145399', 'BE71096123456769', '2026-04-14 02:00:00', 'EUR'),
  (16, 'Payment for books', -15.00, 'FR7618206000572476985163', 'IS140159260076545510730339', '2026-04-15 10:00:00', 'EUR'),
  (17, 'Magazine subscription', -15.00, 'FR7618206000572476985163', 'NO9386011117947', '2026-04-15 18:00:00', 'EUR'),
  (18, 'Payment for car repair', -300.00, 'SK3111000000002923456789', 'PL61109010140000071219812874', '2026-04-16 09:00:00', 'EUR'),
  (19, 'Payment for groceries', -200.00, 'SK3111000000002923456789', 'GB29NWBK60161331926819', '2026-04-16 10:00:00', 'EUR'),
  (20, 'Refund from merchant', 100.00, 'SK3111000000002923456789', 'PL61109010140000071219812874', '2026-04-16 11:00:00', 'EUR'),
  (21, 'Payment for coffee', -3.50, 'SK3111000000002923456789', 'BE71096123456769', '2026-04-17 08:00:00', 'EUR');

-- Accounts
INSERT INTO accounts (iban, name, email) VALUES
  ('SK3111000000002923456789', 'John Doe', 'johndoe@example.com'),
  ('FR7618206000572476985163', 'Bob Smith', 'bobsmith@example.com'),
  ('DE6508000000192000145399', 'Alice Brown', 'alicebrown@example.com'),
  ('NL02ABNA0457893456', 'Charlie Green', 'charliegreen@example.com');
