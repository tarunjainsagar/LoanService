package xyz.jia.model;

public class FeeProjection {
    private String date;
    private double fee;

    public String date() {
        return date;
    }

    public FeeProjection setDate(String date) {
        this.date = date;
        return this;
    }

    public double fee() {
        return fee;
    }

    public FeeProjection setFee(double fee) {
        this.fee = fee;
        return this;
    }
}
