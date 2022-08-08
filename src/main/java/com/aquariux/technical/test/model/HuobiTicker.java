package com.aquariux.technical.test.model;

import lombok.Data;

import java.util.List;

@Data
public class HuobiTicker {
    private List<HuobiData> data;
    private String status;
    private float ts;
}
