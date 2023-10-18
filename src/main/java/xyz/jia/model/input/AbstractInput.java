package xyz.jia.model.input;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.jia.annotation.IConstraintValidator;
import xyz.jia.model.enums.EnumFrequencyType;

/*
 * This class is created keeping in mind that the input response for both api in the requirement have same format.
 * This file can be removed or updated in future based on requirement.
 * */
@Data
@IConstraintValidator
@NoArgsConstructor
public abstract class AbstractInput {

    private Integer amount;

    private Integer duration;

    private EnumFrequencyType durationType;

    private EnumFrequencyType installmentFrequency;

    private String startDate;

    // If this parameter is set true, api will return detailed response
    private boolean showDetails = false;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("amount=").append(amount)
                .append(", duration=").append(duration)
                .append(", durationType=").append(durationType)
                .append(", installmentFrequency=").append(installmentFrequency)
                .append(", startDate='").append(startDate).append("'");
        return sb.toString();
    }
}
