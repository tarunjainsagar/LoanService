package xyz.jia.model.response;

import lombok.Data;

@Data
public class ProjectionReponse {

    private String date;

    private double amount;

    public ProjectionReponse setDate(String date) {
        this.date = date;
        return this;
    }

    public ProjectionReponse setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
