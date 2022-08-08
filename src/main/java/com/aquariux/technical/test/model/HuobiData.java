package com.aquariux.technical.test.model;

import lombok.Data;

@Data
public class HuobiData {
    private String symbol;
    private float open;
    private float high;
    private float low;
    private float close;
    private float amount;
    private float vol;
    private float count;
    private float bid;
    private float bidSize;
    private float ask;
    private float askSize;
}
