package xyz.jia.model.interfaces;

import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.response.AbstractOutput;

public interface ICalculator {

    AbstractOutput calculate(AbstractInput input);
}
