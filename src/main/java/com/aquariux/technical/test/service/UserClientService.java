package com.aquariux.technical.test.service;

import com.aquariux.technical.test.entity.BestAggregatePricesEntity;
import com.aquariux.technical.test.entity.TradeTransactionEntity;
import com.aquariux.technical.test.entity.UserAccountsEntity;

import java.util.List;

public interface UserClientService {
    List<BestAggregatePricesEntity> getAggregateBestPrices();
    UserAccountsEntity getUser(String username);
    void performTrade(TradeTransactionEntity trade) throws Exception;
    List<TradeTransactionEntity> getTradesHistoryByUser(String username);

}
