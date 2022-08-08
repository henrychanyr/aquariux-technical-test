package com.aquariux.technical.test.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "best_aggregate_prices")
@Data
public class BestAggregatePricesEntity implements Serializable {
    @Id
    @Column(name = "ticker_symbol")
    private String tickerSymbol;
    @Column(name = "best_bid_price")
    private float bestBidPrice;
    @Column(name = "best_ask_price")
    private float bestAskPrice;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
}
