package xyz.jia.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.jia.model.enums.EnumFrequencyType;
import xyz.jia.model.input.InstallmentProjectionInput;
import xyz.jia.model.response.FeeProjectionResponse;
import xyz.jia.model.response.ProjectionReponse;
import xyz.jia.utils.CalculatorUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
@RunWith(MockitoJUnitRunner.class)
public class FeeProjectionCalculatorTest {

    @Mock
    private CalculatorUtils calculatorUtils;

    @InjectMocks
    private FeeProjectionCalculator feeProjectionCalculator;

    @Test
    public void testCalculate() {
        int loanAmount = 4000;
        int noOfInstallments = 4;
        InstallmentProjectionInput input = new InstallmentProjectionInput();
        input.setAmount(loanAmount);
        input.setInstallmentFrequency(EnumFrequencyType.MONTHLY);
        input.setDurationType(EnumFrequencyType.MONTHLY);
        input.setDuration(noOfInstallments);

        int principalForEachInstallment = 1000;
        List<ProjectionReponse> installments = new ArrayList<>();

        Mockito.when(calculatorUtils.getNoOfInstallments(4, EnumFrequencyType.MONTHLY, EnumFrequencyType.MONTHLY))
                .thenReturn(noOfInstallments);
        Mockito.when(calculatorUtils.getPrincipalForEachInstallment(loanAmount, noOfInstallments))
                .thenReturn(principalForEachInstallment);
        Mockito.when(calculatorUtils.getInstallments(input, principalForEachInstallment, noOfInstallments, true))
                .thenReturn(installments);

        FeeProjectionResponse response = feeProjectionCalculator.calculate(input);

        assertEquals(loanAmount, (int) response.getActual_loan_amount());
        assertEquals(noOfInstallments, (int) response.getNo_of_installments());
        assertEquals(EnumFrequencyType.MONTHLY, response.getDurationType());
        assertEquals(EnumFrequencyType.MONTHLY, response.getInstallmentFrequency());
        assertEquals(installments, response.getProjections());
    }
}
