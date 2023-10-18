package xyz.jia.config;

import lombok.Data;

@Data
public class FeeParameters {
    private double interest;
    private int interestFrequency;
    private double serviceFee;
    private double serviceFeeCap;
    private int serviceFeeFrequency;
}
