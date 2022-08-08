package com.aquariux.technical.test.constants;

public enum TradeAction {
    BUY("buy"),
    SELL("sell");
    private String value;

    TradeAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
