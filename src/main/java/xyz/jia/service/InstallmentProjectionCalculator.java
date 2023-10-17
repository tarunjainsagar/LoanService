package xyz.jia.service;

import org.springframework.stereotype.Component;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.interfaces.ICalculator;
import xyz.jia.model.response.AbstractOutput;

@Component
public class InstallmentProjectionCalculator implements ICalculator {

    @Override
    public AbstractOutput calculate(String api, AbstractInput input) {
        // todo: implement functionality
        return null;
    }
}
