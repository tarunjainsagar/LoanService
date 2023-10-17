package xyz.jia.model.response;

public class FeeProjectionResponse {
    private String date;
    private double fee;

    public String date() {
        return date;
    }

    public double fee() {
        return fee;
    }

    public FeeProjectionResponse setDate(String date) {
        this.date = date;
        return this;
    }

    public FeeProjectionResponse setFee(double fee) {
        this.fee = fee;
        return this;
    }
}
