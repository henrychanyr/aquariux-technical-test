package com.aquariux.technical.test.model;

import lombok.Data;

@Data
public class BinanceTicker {
    private String symbol;
    private float bidPrice;
    private float bidQty;
    private float askPrice;
    private float askQty;
}
