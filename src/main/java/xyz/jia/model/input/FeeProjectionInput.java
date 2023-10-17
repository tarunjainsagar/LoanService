package xyz.jia.model.input;

import lombok.Data;

@Data
public class FeeProjectionInput implements InputInterface {

    private int amount;

    private int duration;

    private String startDate;

    @Override
    public String toString() {
        return "FeeProjectionInput{" +
                "amount=" + amount +
                ", duration=" + duration +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}

