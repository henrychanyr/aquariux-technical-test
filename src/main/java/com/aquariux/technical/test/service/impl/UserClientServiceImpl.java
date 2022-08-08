package com.aquariux.technical.test.service.impl;

import com.aquariux.technical.test.constants.TickerSymbolConstant;
import com.aquariux.technical.test.constants.TradeAction;
import com.aquariux.technical.test.entity.BestAggregatePricesEntity;
import com.aquariux.technical.test.entity.TradeTransactionEntity;
import com.aquariux.technical.test.entity.UserAccountsEntity;
import com.aquariux.technical.test.model.BinanceTicker;
import com.aquariux.technical.test.model.HuobiData;
import com.aquariux.technical.test.refdata.client.PriceAggregationReferenceClient;
import com.aquariux.technical.test.repository.BestAggregatePriceRepository;
import com.aquariux.technical.test.repository.TradeTransactionRepository;
import com.aquariux.technical.test.repository.UserAccountRepository;
import com.aquariux.technical.test.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserClientServiceImpl implements UserClientService {

    @Value("${supported.tickers}")
    private String supportedTickers;
    @Autowired
    private PriceAggregationReferenceClient priceAggregationReferenceClient;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private BestAggregatePriceRepository bestAggregatePriceRepository;
    @Autowired
    private TradeTransactionRepository tradeTransactionRepository;

    @Override
    public void performTrade(TradeTransactionEntity trade) throws Exception {
        if (trade.getTickerSymbol() == null || trade.getTickerSymbol().isBlank() ||
                trade.getQuantity() == 0 ||
                trade.getTradeAction() == null || trade.getTradeAction().isBlank() ||
                trade.getUsername() == null || trade.getUsername().isBlank()) {
            throw new Exception("Missing field in trade request");
        } else if (!trade.getTradeAction().toLowerCase().equals(TradeAction.BUY.getValue())
                && !trade.getTradeAction().toLowerCase().equals(TradeAction.SELL.getValue())) {
            throw new Exception("Invalid trade action");
        } else {
            UserAccountsEntity user = this.getUser(trade.getUsername());
            if (user == null) {
                throw new Exception("Invalid username");
            }
            List<BestAggregatePricesEntity> bestAggregatePricesEntityList = this.getAggregateBestPrices();
            BestAggregatePricesEntity bestAggregatePricesEntity = bestAggregatePricesEntityList.stream()
                    .filter(ticker -> ticker.getTickerSymbol().equalsIgnoreCase(trade.getTickerSymbol()))
                    .findFirst()
                    .get();

            if (trade.getTradeAction().toLowerCase().equals(TradeAction.BUY.getValue())) {
                if (user.getWalletBalance() < bestAggregatePricesEntity.getBestAskPrice() * trade.getQuantity()) {
                    throw new Exception("Insufficient balance to perform trade.");
                } else {
                    user.setWalletBalance(user.getWalletBalance() - (bestAggregatePricesEntity.getBestAskPrice() * trade.getQuantity()));
                    if (trade.getTickerSymbol().toLowerCase().equals(TickerSymbolConstant.BTCUSDT)) {
                        user.setExistingBtcusdtQuantity(user.getExistingBtcusdtQuantity() + trade.getQuantity());
                    }
                    if (trade.getTickerSymbol().toLowerCase().equals(TickerSymbolConstant.ETHUSDT)) {
                        user.setExistingEthusdtQuantity(user.getExistingEthusdtQuantity() + trade.getQuantity());
                    }
                }
            } else if (trade.getTradeAction().toLowerCase().equals(TradeAction.SELL.getValue())) {
                int userQuantity = trade.getTickerSymbol().toLowerCase().equals(TickerSymbolConstant.ETHUSDT) ?
                        user.getExistingEthusdtQuantity() : user.getExistingBtcusdtQuantity();
                if (trade.getQuantity() > userQuantity) {
                    throw new Exception("Insufficient quantity to sell");
                } else {
                    if (trade.getTickerSymbol().toLowerCase().equals(TickerSymbolConstant.BTCUSDT)) {
                        user.setExistingBtcusdtQuantity(user.getExistingBtcusdtQuantity() - trade.getQuantity());
                        user.setWalletBalance(user.getWalletBalance() + (trade.getQuantity() * bestAggregatePricesEntity.getBestBidPrice()));
                    }
                    if (trade.getTickerSymbol().toLowerCase().equals(TickerSymbolConstant.ETHUSDT)) {
                        user.setExistingEthusdtQuantity(user.getExistingEthusdtQuantity() - trade.getQuantity());
                        user.setWalletBalance(user.getWalletBalance() + (trade.getQuantity() * bestAggregatePricesEntity.getBestBidPrice()));
                    }
                }
            }

            TradeTransactionEntity tradeTransactionEntity = new TradeTransactionEntity();
            tradeTransactionEntity.setUsername(trade.getUsername());
            tradeTransactionEntity.setTickerSymbol(trade.getTickerSymbol().toLowerCase());
            tradeTransactionEntity.setTradeAction(trade.getTradeAction().toLowerCase());
            tradeTransactionEntity.setQuantity(trade.getQuantity());
            tradeTransactionRepository.save(tradeTransactionEntity);
            userAccountRepository.save(user);
        }
    }

    @Override
    public UserAccountsEntity getUser(String username) {
        Optional<UserAccountsEntity> userAccountsEntityOptional = userAccountRepository.findByUsername(username);
        return userAccountsEntityOptional.isPresent() ? userAccountsEntityOptional.get() : null;
    }

    @Override
    public List<TradeTransactionEntity> getTradesHistoryByUser(String username) {
        return tradeTransactionRepository.findByUsername(username);
    }

    @Override
    @PostConstruct
    public List<BestAggregatePricesEntity> getAggregateBestPrices() {
        List<BestAggregatePricesEntity> bestAggregatePriceList = new ArrayList<>();
        List<String> tickerSymbolList = Arrays.asList(supportedTickers.split(","));
        List<HuobiData> huobiDataList = priceAggregationReferenceClient.getHuobiTickerSymbol();
        List<BinanceTicker> binanceTickerList = priceAggregationReferenceClient.getBinanceTickerSymbol();

        tickerSymbolList.stream().forEach(tickerSymbol -> {
            BestAggregatePricesEntity bestAggregatePricesEntity = new BestAggregatePricesEntity();
            bestAggregatePricesEntity.setTickerSymbol(tickerSymbol);
            bestAggregatePricesEntity.setUpdatedOn(LocalDateTime.now());
            HuobiData huobiData = huobiDataList.stream()
                    .filter(data -> data.getSymbol().equalsIgnoreCase(tickerSymbol))
                    .findFirst().get();
            BinanceTicker binanceTicker = binanceTickerList.stream()
                    .filter(data -> data.getSymbol().equalsIgnoreCase(tickerSymbol))
                    .findFirst().get();
            float price;

            //  Find best Bid price
            price = huobiData.getBid() <= binanceTicker.getBidPrice() ? huobiData.getBid() : binanceTicker.getBidPrice();
            bestAggregatePricesEntity.setBestBidPrice(price);

            //  Find best Ask Price
            price = huobiData.getAsk() <= binanceTicker.getAskPrice() ? huobiData.getAsk() : binanceTicker.getAskPrice();
            bestAggregatePricesEntity.setBestAskPrice(price);

            bestAggregatePriceList.add(bestAggregatePricesEntity);
        });
        bestAggregatePriceRepository.saveAll(bestAggregatePriceList);
        return bestAggregatePriceList;
    }

    @PostConstruct
    private void initiateUserAccountDatabase() {
        UserAccountsEntity userAccountsEntity = new UserAccountsEntity();
        userAccountsEntity.setId(1L);
        userAccountsEntity.setUsername("user1");
        userAccountsEntity.setWalletBalance(50000);
        userAccountsEntity.setExistingEthusdtQuantity(0);
        userAccountsEntity.setExistingBtcusdtQuantity(0);
        userAccountsEntity.setCreatedOn(LocalDateTime.now());
        userAccountsEntity.setUpdatedOn(LocalDateTime.now());
        userAccountRepository.save(userAccountsEntity);
    }
}
