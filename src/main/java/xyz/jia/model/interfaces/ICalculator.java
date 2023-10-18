package xyz.jia.model.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.response.AbstractOutput;

public interface ICalculator {

    @Autowired

    AbstractOutput calculate(AbstractInput input);
}
