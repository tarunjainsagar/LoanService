package xyz.jia.model.response;

import lombok.Data;

@Data
public class ProjectionReponse {

    private String date;

    private double amount;

    private String remark;

    public ProjectionReponse setDate(String date) {
        this.date = date;
        return this;
    }

    public ProjectionReponse setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public ProjectionReponse setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
