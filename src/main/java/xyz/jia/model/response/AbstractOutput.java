package xyz.jia.model.response;

import lombok.Data;
import xyz.jia.model.enums.EnumFrequencyType;

import java.util.List;

@Data
/*
 * This class is created keeping in mind that the output response for both api in the requirement have same format.
 * This file can be removed or updated in future based on requirement.
 * */
public abstract class AbstractOutput {

    private Integer actual_loan_amount;
    private Integer total_interest_amount;
    private Integer total_service_fee_amount;
    private Integer total_payable_amount;
    private Integer no_of_installments;
    private EnumFrequencyType durationType;
    private EnumFrequencyType installmentFrequency;
    List<ProjectionReponse> projections;

    public StringBuilder buildOutput(boolean showDetails) {
        StringBuilder sb = new StringBuilder();

        if (showDetails) {
            sb.append("actual_loan_amount=").append(actual_loan_amount).append(System.lineSeparator())
                    .append("total_interest_amount=").append(total_interest_amount).append(System.lineSeparator())
                    .append("total_service_fee_amount=").append(total_service_fee_amount).append(System.lineSeparator())
                    .append("total_payable_amount=").append(total_payable_amount).append(System.lineSeparator())
                    .append("no_of_installments=").append(no_of_installments).append(System.lineSeparator())
                    .append("durationType=").append(durationType).append(System.lineSeparator())
                    .append("installmentFrequency=").append(installmentFrequency).append(System.lineSeparator())
                    .append("Projections:").append(System.lineSeparator());
        }

        for (ProjectionReponse response : projections) {
            // Append each date and amount in the specified format
            sb.append(response.getDate()).append(" => ").append(response.getAmount()).append(System.lineSeparator());
        }
        return sb;
    }
}
