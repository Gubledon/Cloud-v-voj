-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;
insert into fee (id, iban, amount, postingDate, transactionId) values (100, 'SK8975000000000012345671', '2', '2024-01-01', '83f3d5b6-7ca2-4f1d-838c-4d1cbfb1e1d8');
insert into fee (id, iban, amount, postingDate, transactionId) values (101, 'SK8975000000000012345671', '0.01', '2024-02-01', 'ceab21f0-fc20-4ed7-a6e8-6ac26ead5f39');
insert into fee (id, iban, amount, postingDate, transactionId) values (102, 'SK3112000000198742637541', '0.01', '2024-03-01', '791ba284-1491-40cd-a0f0-30589644ac2b');
insert into fee (id, iban, amount, postingDate, transactionId) values (103, 'SK3112000000198742637541', '0.01', '2024-03-02', '84ae8704-a0f0-4e0d-a1c7-919ba3a3ebdf');
insert into fee (id, iban, amount, postingDate, transactionId) values (104, 'SK3112000000198742637541', '2', '2024-03-03', '5cb19bcc-3212-4ba1-b2fb-3df59fb23379');
insert into fee (id, iban, amount, postingDate, transactionId) values (105, 'CZ5508000000001234567899', '2', '2024-03-20', 'd6b6a7c8-c4cb-4f00-be22-46a688ddb01e');
