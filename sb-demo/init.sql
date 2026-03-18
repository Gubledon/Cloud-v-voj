CREATE TABLE IF NOT EXISTS public.transaction (
    transaction_id varchar(255) PRIMARY KEY,
    amount numeric(38, 2) NOT NULL,
    currency varchar(255) NOT NULL,
    description varchar(255),
    effective_date date,
    constant_symbol varchar(255),
    payer_reference varchar(255),
    specific_symbol varchar(255),
    variable_symbol varchar(255),
    sender_account_balance_value numeric(38, 2),
    sender_account_balance_currency varchar(255),
    sender_account_bic varchar(255),
    sender_account_iban varchar(255),
    sender_account_name varchar(255),
    status varchar(255) NOT NULL,
    target_account_balance_value numeric(38, 2),
    target_account_balance_currency varchar(255),
    target_account_bic varchar(255),
    target_account_iban varchar(255),
    target_account_name varchar(255)
);

INSERT INTO public.transaction (
    transaction_id, amount, currency, description, effective_date,
    constant_symbol, payer_reference, specific_symbol, variable_symbol,
    sender_account_balance_value, sender_account_balance_currency,
    sender_account_bic, sender_account_iban, sender_account_name, status,
    target_account_balance_value, target_account_balance_currency,
    target_account_bic, target_account_iban, target_account_name
) VALUES
('5ae57f2a-5296-4643-876b-da3d5532feb2', 42, 'EUR', 'Anvil', '2022-09-24', null, null, null, '123456', 958, 'EUR', null, 'SK8975000000000012345671', null, 'PROCESSED', null, null, 'DEUTDEFFXXX', 'DE89370400440532013000', 'Acme corp.'),
('6fdd23ca-abd3-40e7-8e3d-ecec2ec80036', 12, 'EUR', 'Cashback', '2022-09-25', null, null, null, null, null, null, 'DEUTDEFFXXX', 'DE89370400440532013000', 'Acme corp.', 'PROCESSED', 970, 'EUR', null, 'SK8975000000000012345671', null),
('a77faf16-7a19-45ea-b625-9587701510e2', 10, 'EUR', 'Cashback', '2022-09-26', null, null, null, null, null, null, 'DEUTDEFFXXX', 'DE89370400440532013000', 'Acme corp.', 'PROCESSED', 1010, 'EUR', null, 'SK3112000000198742637541', null);
