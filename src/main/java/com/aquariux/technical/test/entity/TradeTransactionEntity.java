package com.aquariux.technical.test.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade_transactions")
@Data
//@Builder
public class TradeTransactionEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "ticker_symbol")
    private String tickerSymbol;
    @Column(name = "trade_action")
    private String tradeAction;
    @Column(name = "quantity")
    private int quantity;
    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    public TradeTransactionEntity() {
    }
}
