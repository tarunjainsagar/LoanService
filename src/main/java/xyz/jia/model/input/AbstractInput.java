package xyz.jia.model.input;

import lombok.Data;

/*
 * This class is created keeping in mind that the input response for both api in the requirement have same format.
 * This file can be removed or updated in future based on requirement.
 * */
@Data
public abstract class AbstractInput {

    private Integer amount;

    private Integer duration;

    private String startDate;

    public String toString() {
        return "amount=".concat(amount.toString())
                .concat(", duration=").concat(duration.toString())
                .concat(", startDate='").concat(startDate);
    }
}
