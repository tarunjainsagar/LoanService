package xyz.jia.model.response;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    private Integer no_of_installments;
    private EnumFrequencyType durationType;
    private EnumFrequencyType installmentFrequency;
    List<ProjectionReponse> projections;

    /*
    * todo: Enhance response with more details in future
            private Integer total_interest_amount;
            private Integer total_service_fee_amount;
            private Integer total_payable_amount;
    */

    public ObjectNode buildOutput(boolean showDetails) {
        ObjectNode jsonOutput = JsonNodeFactory.instance.objectNode();

        if (showDetails) {
            jsonOutput.put("actual_loan_amount", actual_loan_amount);
            jsonOutput.put("no_of_installments", no_of_installments);
            jsonOutput.put("durationType", durationType != null ? durationType.toString() : "");
            jsonOutput.put("installmentFrequency", installmentFrequency != null ? installmentFrequency.toString() : "");

            /*
             * todo: Enhance response with more details in future
                    jsonOutput.put("total_interest_amount", total_interest_amount);
                    jsonOutput.put("total_service_fee_amount", total_service_fee_amount);
                    jsonOutput.put("total_payable_amount", total_payable_amount);
            */
        }

        ArrayNode dataNode = JsonNodeFactory.instance.arrayNode();
        for (ProjectionReponse response : projections) {
            ObjectNode projection = JsonNodeFactory.instance.objectNode();
            projection.put("date", response.getDate());
            projection.put("amount", response.getAmount());
            projection.put("remark", response.getRemark());
            dataNode.add(projection);
        }
        jsonOutput.set("Projections", dataNode);
        return jsonOutput;
    }
}
