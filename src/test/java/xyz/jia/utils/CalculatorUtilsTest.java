package xyz.jia.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.jia.config.ConstraintsConfiguration;
import xyz.jia.config.FeeParameters;
import xyz.jia.config.FeesConfiguration;
import xyz.jia.config.LoanConfiguration;
import xyz.jia.model.enums.EnumFrequencyType;
import xyz.jia.model.input.FeeProjectionInput;
import xyz.jia.model.input.InstallmentProjectionInput;
import xyz.jia.model.response.ProjectionReponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorUtilsTest {

    @InjectMocks
    private CalculatorUtils calculatorUtils;

    @Before
    public void setUp() {
        LoanConfiguration loanConfiguration = new LoanConfiguration();
        ConstraintsConfiguration constraintsConfiguration = new ConstraintsConfiguration();
        constraintsConfiguration.setDaysInMonth(30);
        constraintsConfiguration.setDaysInWeek(7);
        constraintsConfiguration.setMinAmount(0);
        constraintsConfiguration.setMinMonthlyDuration(1);
        constraintsConfiguration.setMaxMonthlyDuration(12);
        constraintsConfiguration.setMinWeeklyDuration(1);
        constraintsConfiguration.setMaxWeeklyDuration(4);

        FeeParameters weeklyFees = new FeeParameters();
        weeklyFees.setInterest(1.0);
        weeklyFees.setServiceFee(0.5);
        weeklyFees.setServiceFeeCap(50.0);
        weeklyFees.setServiceFeeFrequency(2);

        FeeParameters monthlyFees = new FeeParameters();
        monthlyFees.setInterest(4.0);
        monthlyFees.setServiceFee(0.5);
        monthlyFees.setServiceFeeCap(100.0);
        monthlyFees.setServiceFeeFrequency(3);

        loanConfiguration.setFees(new FeesConfiguration());
        loanConfiguration.getFees().setWeekly(weeklyFees);
        loanConfiguration.getFees().setMonthly(monthlyFees);
        loanConfiguration.setConstraints(constraintsConfiguration);

        calculatorUtils = new CalculatorUtils();
        calculatorUtils.loanConfiguration = loanConfiguration;
    }

    @Test
    public void testGetNoOfInstallmentsForWeekly() {
        int loanDuration = 5;
        EnumFrequencyType durationType = EnumFrequencyType.WEEKLY;
        EnumFrequencyType installmentFrequency = EnumFrequencyType.WEEKLY;
        int result = calculatorUtils.getNoOfInstallments(loanDuration, durationType, installmentFrequency);
        assertEquals(5, result);
    }

    @Test
    public void testGetNoOfInstallmentsForMonthly() {
        int loanDuration = 6;
        EnumFrequencyType durationType = EnumFrequencyType.MONTHLY;
        EnumFrequencyType installmentFrequency = EnumFrequencyType.MONTHLY;
        int result = calculatorUtils.getNoOfInstallments(loanDuration, durationType, installmentFrequency);
        assertEquals(6, result);
    }

    @Test
    public void testGetNoOfInstallmentsMonthlyToWeekly() {
        int loanDuration = 3;
        EnumFrequencyType durationType = EnumFrequencyType.MONTHLY;
        EnumFrequencyType installmentFrequency = EnumFrequencyType.WEEKLY;
        int result = calculatorUtils.getNoOfInstallments(loanDuration, durationType, installmentFrequency);
        assertEquals(13, result);
    }

    @Test
    public void testGetPrincipalForEachInstallment() {
        int loanAmount = 1000;
        int noOfInstallments = 10;
        int result = calculatorUtils.getPrincipalForEachInstallment(loanAmount, noOfInstallments);
        assertEquals(100, result);
    }

    @Test
    public void testGetInstallmentsOnlyFees() {
        FeeProjectionInput input = new FeeProjectionInput();
        input.setAmount(1000);
        input.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        input.setStartDate(dateFormat.format(new Date()));
        int principalForEachInstallment = 100;
        int noOfInstallments = 4;
        boolean onlyFees = true;

        List<ProjectionReponse> installments = calculatorUtils.getInstallments(input, principalForEachInstallment, noOfInstallments, onlyFees);

        assertEquals(4, installments.size());
        assertEquals(10.0, installments.get(0).getAmount(), 0.0);
        assertEquals(15.0, installments.get(1).getAmount(), 0.0);
        assertEquals(10.0, installments.get(2).getAmount(), 0.0);
        assertEquals(15.0, installments.get(3).getAmount(), 0.0);
    }

    @Test
    public void testGetInstallmentsWithPrincipal() {
        InstallmentProjectionInput input = new InstallmentProjectionInput();
        input.setAmount(1000);
        input.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        input.setStartDate(dateFormat.format(new Date()));
        int principalForEachInstallment = 100;
        int noOfInstallments = 4;
        boolean onlyFees = false;

        List<ProjectionReponse> installments = calculatorUtils.getInstallments(input, principalForEachInstallment, noOfInstallments, onlyFees);

        assertEquals(4, installments.size());
        assertEquals(110.0, installments.get(0).getAmount(), 0.0);
        assertEquals(115.0, installments.get(1).getAmount(), 0.0);
        assertEquals(110.0, installments.get(2).getAmount(), 0.0);
        assertEquals(115.0, installments.get(3).getAmount(), 0.0);
    }

    @Test
    public void testGetInstallmentsWithMonthlyInstallments() {
        InstallmentProjectionInput input = new InstallmentProjectionInput();
        input.setAmount(2000);
        input.setInstallmentFrequency(EnumFrequencyType.MONTHLY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        input.setStartDate(dateFormat.format(new Date()));
        int principalForEachInstallment = 200;
        int noOfInstallments = 10;
        boolean onlyFees = false;

        List<ProjectionReponse> installments = calculatorUtils.getInstallments(input, principalForEachInstallment, noOfInstallments, onlyFees);

        assertEquals(10, installments.size());
        assertEquals(280.0, installments.get(0).getAmount(), 0.0);
        assertEquals(280.0, installments.get(1).getAmount(), 0.0);
        assertEquals(290.0, installments.get(2).getAmount(), 0.0);
        assertEquals(280.0, installments.get(3).getAmount(), 0.0);
        assertEquals(280.0, installments.get(4).getAmount(), 0.0);
        assertEquals(290.0, installments.get(5).getAmount(), 0.0);
        assertEquals(280.0, installments.get(6).getAmount(), 0.0);
        assertEquals(280.0, installments.get(7).getAmount(), 0.0);
        assertEquals(290.0, installments.get(8).getAmount(), 0.0);
        assertEquals(280.0, installments.get(9).getAmount(), 0.0);
    }
    @Test
    public void testGetInstallmentsWithServiceFeeCap() {
        InstallmentProjectionInput input = new InstallmentProjectionInput();
        input.setAmount(20000);
        input.setInstallmentFrequency(EnumFrequencyType.MONTHLY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        input.setStartDate(dateFormat.format(new Date()));
        int principalForEachInstallment = 2000;
        int noOfInstallments = 10;
        boolean onlyFees = false;

        List<ProjectionReponse> installments = calculatorUtils.getInstallments(input, principalForEachInstallment, noOfInstallments, onlyFees);

        assertEquals(10, installments.size());
        assertEquals(2800.0, installments.get(0).getAmount(), 0.0);
        assertEquals(2800.0, installments.get(1).getAmount(), 0.0);
        assertEquals(2900.0, installments.get(2).getAmount(), 0.0);
        assertEquals(2800.0, installments.get(3).getAmount(), 0.0);
        assertEquals(2800.0, installments.get(4).getAmount(), 0.0);
        assertEquals(2900.0, installments.get(5).getAmount(), 0.0);
        assertEquals(2800.0, installments.get(6).getAmount(), 0.0);
        assertEquals(2800.0, installments.get(7).getAmount(), 0.0);
        assertEquals(2900.0, installments.get(8).getAmount(), 0.0);
        assertEquals(2800.0, installments.get(9).getAmount(), 0.0);
    }

}
