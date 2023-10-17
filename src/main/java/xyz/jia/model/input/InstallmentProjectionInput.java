package xyz.jia.model.input;

import lombok.Data;

@Data
public class InstallmentProjectionInput implements InputInterface {

    private int amount;

    private int duration;

    private String startDate;

    @Override
    public String toString() {
        return "InstallmentProjectionInput{" +
                "amount=" + amount +
                ", duration=" + duration +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}

