DROP TABLE IF EXISTS best_aggregate_prices;
DROP TABLE IF EXISTS user_accounts;
DROP TABLE IF EXISTS trade_transactions;

CREATE TABLE best_aggregate_prices (
ticker_symbol varchar(10),
best_bid_price FLOAT,
best_ask_price FLOAT,
updated_on TIMESTAMP NOT NULL DEFAULT now()
);


CREATE TABLE user_accounts (
id BIGSERIAL PRIMARY KEY,
username varchar(50),
wallet_balance FLOAT,
existing_btcusdt_quantity INTEGER,
existing_ethusdt_quantity INTEGER,
created_on TIMESTAMP NOT NULL DEFAULT now(),
updated_on TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE trade_transactions (
id BIGSERIAL PRIMARY KEY,
ticker_symbol VARCHAR(10) NOT NULL,
trade_action VARCHAR(10) NOT NULL,
quantity VARCHAR(6) NOT NULL,
created_on TIMESTAMP NOT NULL DEFAULT now()
);