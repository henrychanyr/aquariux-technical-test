package com.aquariux.technical.test.controller;

import com.aquariux.technical.test.entity.BestAggregatePricesEntity;
import com.aquariux.technical.test.entity.TradeTransactionEntity;
import com.aquariux.technical.test.entity.UserAccountsEntity;
import com.aquariux.technical.test.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersClientController {

    @Autowired
    private UserClientService service;

    //  2. Create an api to retrieve the latest best aggregated price.
    @RequestMapping("/bestAggregatePriceList")
    public ResponseEntity<List<BestAggregatePricesEntity>> getBestAggregatePrice() {
        List<BestAggregatePricesEntity> priceList = service.getAggregateBestPrices();
        return ResponseEntity.ok(priceList);
    }

    //  3. Create an api which allows users to trade based on the latest best aggregated price.
    @PostMapping("/trade")
    public void createNewTrade(@RequestBody TradeTransactionEntity trade) throws Exception {
        service.performTrade(trade);
    }

    //  4. Create an api to retrieve the user’s crypto currencies wallet balance
    @RequestMapping("/getUser/{username}")
    public ResponseEntity<UserAccountsEntity> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(service.getUser(username));
    }

    //  5. Create an api to retrieve the user trading history.
    @RequestMapping("getTradingHistory/{username}")
    public ResponseEntity<List<TradeTransactionEntity>> getTradingHistory(@PathVariable("username") String username) {
        return ResponseEntity.ok(service.getTradesHistoryByUser(username));
    }
}
