package xyz.jia.service;

import org.springframework.stereotype.Component;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.interfaces.ICalculator;
import xyz.jia.model.response.AbstractOutput;
import xyz.jia.model.response.FeeProjectionResponse;
import xyz.jia.model.response.ProjectionReponse;

import java.util.Arrays;

@Component
public class FeeProjectionCalculator implements ICalculator {

    @Override
    public AbstractOutput calculate(String api, AbstractInput input) {
        // todo: implement functionality

        /*
        * dummy implementation to check flow
        * */
        FeeProjectionResponse fpr = new FeeProjectionResponse();
        fpr.setProjectionResponseList(Arrays.asList(
                new ProjectionReponse().setAmount(100).setDate("01/10/2023"),
                new ProjectionReponse().setAmount(200).setDate("01/10/2025"),
                new ProjectionReponse().setAmount(300).setDate("01/10/2024")
        ));
        return fpr;
    }
}
