INSERT INTO transaction (transaction_id, status, amount, currency, effective_date, description,
    sender_account_iban, sender_account_name, sender_account_bic, sender_account_balance_value, sender_account_balance_currency,
    target_account_iban, target_account_name, target_account_bic, target_account_balance_value, target_account_balance_currency,
    variable_symbol, specific_symbol, constant_symbol, payer_reference)
VALUES
    ('667bb620-04c6-45cb-8746-80cf932c7a01', 'PROCESSED', 42.00, 'EUR', '2022-09-24', 'Anvil',
     'SK8975000000000012345671', 'John Doe', 'TATRSKBX', 958.00, 'EUR',
     'DE89370400440532013000', 'Acme corp.', 'DEUTDEFFXXX', NULL, NULL,
     '123456', NULL, NULL, NULL),

    ('1717c635-d19a-4399-b911-2d30578c065d', 'PROCESSED', 10.00, 'EUR', '2022-09-25', 'Cashback',
     'DE89370400440532013000', 'Acme corp.', 'DEUTDEFFXXX', NULL, NULL,
     'SK8975000000000012345671', 'John Doe', 'TATRSKBX', 970.00, 'EUR',
     NULL, NULL, NULL, NULL),

    ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'APPROVED', 150.00, 'EUR', '2022-09-26', 'Monthly rent',
     'SK8975000000000012345671', 'John Doe', 'TATRSKBX', 820.00, 'EUR',
     'SK3112000000198742637541', 'Landlord s.r.o.', 'GIBASKBX', NULL, NULL,
     '202209', '0308', NULL, NULL),

    ('b2c3d4e5-f6a7-8901-bcde-f12345678901', 'PROCESSED', 500.00, 'EUR', '2022-09-20', 'Salary',
     'SK6807200002891987426375', 'Employer a.s.', 'NBSBSKBX', NULL, NULL,
     'SK8975000000000012345671', 'John Doe', 'TATRSKBX', 1000.00, 'EUR',
     '092022', NULL, NULL, NULL);
