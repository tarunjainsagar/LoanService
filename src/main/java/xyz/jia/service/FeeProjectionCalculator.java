package xyz.jia.service;

import org.springframework.stereotype.Component;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.interfaces.ICalculator;
import xyz.jia.model.response.FeeProjectionResponse;
import xyz.jia.model.response.ProjectionReponse;

import java.util.Arrays;

@Component
public class FeeProjectionCalculator implements ICalculator {

    @Override
    public FeeProjectionResponse calculate(AbstractInput input) {

        // todo: implement functionality

        /*
        * dummy output for testing
        * */
        FeeProjectionResponse fpr = new FeeProjectionResponse();
        fpr.setProjections(Arrays.asList(
                // below code is sample, replace by actual installment
                new ProjectionReponse().setAmount(100).setDate("01/10/2023"),
                new ProjectionReponse().setAmount(200).setDate("01/10/2025"),
                new ProjectionReponse().setAmount(300).setDate("01/10/2024")
        ));
        return fpr;
    }
}
