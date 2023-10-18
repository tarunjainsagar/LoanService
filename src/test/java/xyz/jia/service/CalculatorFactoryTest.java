package xyz.jia.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.jia.constants.UriConstants;
import xyz.jia.exception.InvalidRequestException;
import xyz.jia.model.input.FeeProjectionInput;
import xyz.jia.model.input.InstallmentProjectionInput;
import xyz.jia.model.response.FeeProjectionResponse;
import xyz.jia.model.response.InstallmentProjectionResponse;
import xyz.jia.utils.LogUtils;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorFactoryTest {

    @InjectMocks
    private CalculatorFactory calculatorFactory;

    @Mock
    private LogUtils logUtils;

    @Mock
    private FeeProjectionCalculator feeProjectionCalculator;

    @Mock
    private InstallmentProjectionCalculator installmentProjectionCalculator;

    @Test
    public void testCalculateFeeProjection() {
        FeeProjectionInput input = new FeeProjectionInput();
        input.setShowDetails(false);
        String api = UriConstants.feeProjectionApi;
        FeeProjectionResponse fpr = new FeeProjectionResponse();
        fpr.setProjections(new ArrayList<>());

        when(feeProjectionCalculator.calculate(input)).thenReturn(fpr);

        ObjectNode result = calculatorFactory.calculate(api, input);

        assertEquals(fpr.buildOutput(false), result);
    }

    @Test
    public void testCalculateInstallmentProjection() {
        InstallmentProjectionInput input = new InstallmentProjectionInput();
        input.setShowDetails(false);
        String api = UriConstants.installmentProjectionApi;
        InstallmentProjectionResponse ipr = new InstallmentProjectionResponse();
        ipr.setProjections(new ArrayList<>());

        when(installmentProjectionCalculator.calculate(input)).thenReturn(ipr);

        ObjectNode result = calculatorFactory.calculate(api, input);

        assertEquals(ipr.buildOutput(false), result);
    }

    @Test(expected = InvalidRequestException.class)
    public void testCalculateInvalidApi() {
        InstallmentProjectionInput input = new InstallmentProjectionInput();
        String api = "invalid_api";
        calculatorFactory.calculate(api, input);
    }
}
