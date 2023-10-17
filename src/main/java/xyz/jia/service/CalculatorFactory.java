package xyz.jia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.jia.constants.UrlConstants;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.interfaces.ICalculator;
import xyz.jia.utils.FormatUtils;
import xyz.jia.utils.LogUtils;

import static xyz.jia.constants.UrlConstants.baseLoanApi;

@Component
@RequiredArgsConstructor
public class CalculatorFactory {
    private static FeeProjectionCalculator feeProjectionCalculator;
    private static InstallmentProjectionCalculator installmentProjectionCalculator;

    @Autowired
    CalculatorFactory(FeeProjectionCalculator feeProjectionCalculator, InstallmentProjectionCalculator installmentProjectionCalculator) {
        CalculatorFactory.feeProjectionCalculator = feeProjectionCalculator;
        CalculatorFactory.installmentProjectionCalculator = installmentProjectionCalculator;
    }

    public static String calculate(String api, AbstractInput input) {
        ICalculator calculatorInterface;

        if (UrlConstants.feeProjectionApi.equals(api)) {
            calculatorInterface = feeProjectionCalculator;
        } else if (UrlConstants.installmentProjectionApi.equals(api)) {
            calculatorInterface = installmentProjectionCalculator;
        } else {
            // todo: handle exception
            throw new RuntimeException();
        }
        LogUtils.logRequestDetails(baseLoanApi, api, input);
        return FormatUtils.formatResponse(calculatorInterface.calculate(api, input));
    }




}
