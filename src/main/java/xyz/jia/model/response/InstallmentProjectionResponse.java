package xyz.jia.model.response;

public class InstallmentProjectionResponse {
    private String date;
    private double amount;

    public String date() {
        return date;
    }

    public double amount() {
        return amount;
    }

    public InstallmentProjectionResponse setDate(String date) {
        this.date = date;
        return this;
    }

    public InstallmentProjectionResponse setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
