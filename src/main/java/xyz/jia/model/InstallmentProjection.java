package xyz.jia.model;

public class InstallmentProjection {
    private String date;
    private double amount;

    public String date() {
        return date;
    }

    public InstallmentProjection setDate(String date) {
        this.date = date;
        return this;
    }

    public double amount() {
        return amount;
    }

    public InstallmentProjection setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
