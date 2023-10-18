package xyz.jia.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.jia.constants.UriConstants;
import xyz.jia.exception.InvalidRequestException;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.interfaces.ICalculator;
import xyz.jia.utils.LogUtils;

import static xyz.jia.constants.UriConstants.baseLoanApi;

@Component
@RequiredArgsConstructor
public class CalculatorFactory {

    @Autowired
    private LogUtils logUtils;
    @Autowired
    private FeeProjectionCalculator feeProjectionCalculator;
    @Autowired
    private InstallmentProjectionCalculator installmentProjectionCalculator;

    public ObjectNode calculate(String api, AbstractInput input) {
        ICalculator calculatorInterface;

        if (UriConstants.feeProjectionApi.equals(api)) {
            calculatorInterface = feeProjectionCalculator;
        } else if (UriConstants.installmentProjectionApi.equals(api)) {
            calculatorInterface = installmentProjectionCalculator;
        } else {
            throw new InvalidRequestException(api);
        }

        /*
            // Note: This functionality is replaced using @IConstraintValidator
            boolean isValidRequest = input.validateInput();
            if(!isValidRequest) {
                throw new RuntimeException();
            }
         */
        logUtils.logRequestDetails(baseLoanApi, api, input);
        return calculatorInterface.calculate(input).buildOutput(input.isShowDetails());
    }
}
