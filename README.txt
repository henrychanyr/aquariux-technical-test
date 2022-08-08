APIs
----------------------------------------------------
http://localhost:8080/bestAggregatePriceList
http://localhost:8080/trade
http://localhost:8080/getUser/user1
http://localhost:8080/getTradingHistory/user1

Post Json
----------------------------------------------------
{
  "username": "user1",
  "tickerSymbol": "BTCUSDT",
  "tradeAction": "Buy",
  "quantity": 1
}

{
  "username": "user1",
  "tickerSymbol": "BTCUSDT",
  "tradeAction": "SELL",
  "quantity": 1
}