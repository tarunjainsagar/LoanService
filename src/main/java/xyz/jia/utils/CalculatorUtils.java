package xyz.jia.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.jia.config.FeeParameters;
import xyz.jia.config.LoanConfiguration;
import xyz.jia.model.enums.EnumFrequencyType;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.response.ProjectionReponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalculatorUtils {
    private static LoanConfiguration loanConfiguration;

    @Autowired
    public CalculatorUtils(LoanConfiguration loanConfiguration) {
        CalculatorUtils.loanConfiguration = loanConfiguration;
    }

    public static int getNoOfInstallments(int loanDuration, EnumFrequencyType durationType, EnumFrequencyType installmentFrequency) {
        int noOfInstallments = loanDuration;

        if (durationType == EnumFrequencyType.MONTHLY && installmentFrequency == EnumFrequencyType.WEEKLY) {
            // convert months to weeks
            int totalDaysToPayLoan = loanDuration * CalculatorUtils.loanConfiguration.getConstraints().getDaysInMonth();
            double totalWeeks = (double) totalDaysToPayLoan / CalculatorUtils.loanConfiguration.getConstraints().getDaysInWeek();
            noOfInstallments = (int) Math.ceil(totalWeeks);
        }

        return noOfInstallments;
    }

    public static int getPrincipalForEachInstallment(int loanAmount, int noOfInstallments) {
        // principal evenly spread across all installments
        double principalForEachInstallment = (double) loanAmount / noOfInstallments;
        return (int) Math.ceil(principalForEachInstallment);
    }

    public static List<ProjectionReponse> getInstallments(AbstractInput input,
                                                           int principalForEachInstallment,
                                                           int noOfInstallments) {
        List<ProjectionReponse> installments = new ArrayList<>();
        Integer loanAmount = input.getAmount();
        int daysInFrequency;
        FeeParameters feesParams;

        if (input.getInstallmentFrequency() == EnumFrequencyType.WEEKLY) {
            // Weekly Installment
            feesParams = loanConfiguration.getFees().getWeekly();
            daysInFrequency = loanConfiguration.getConstraints().getDaysInWeek();
        } else {
            // Monthly Installment
            feesParams = loanConfiguration.getFees().getMonthly();
            daysInFrequency = loanConfiguration.getConstraints().getDaysInMonth();
        }

        double interestRate = feesParams.getInterest() / 100.0;
        double serviceFeeRate = feesParams.getServiceFee() / 100.0;
        double serviceFeeCap = feesParams.getServiceFeeCap();
        int serviceFeeFrequency = feesParams.getServiceFeeFrequency();

        double fixedInterest = loanAmount * interestRate;
        double fixedServiceFees = loanAmount * serviceFeeRate;
        LocalDate startDate = LocalDate.parse(input.getStartDate());

        int serviceFeeInstallment = 1;
        for (int installmentCount = 0; installmentCount < noOfInstallments; installmentCount++) {
            double installmentAmount = principalForEachInstallment + fixedInterest;
            double serviceFee = 0;
            LocalDate installmentDate = startDate.plusDays((long) daysInFrequency * installmentCount);

            if (serviceFeeInstallment % serviceFeeFrequency == 0) {
                serviceFee = Math.min(fixedServiceFees, serviceFeeCap);
                installmentAmount += serviceFee;
                serviceFeeInstallment = 1;
            } else {
                serviceFeeInstallment++;
            }

            installments.add(new ProjectionReponse()
                    .setAmount(installmentAmount)
                    .setDate(installmentDate.toString())
                    .setRemark(generateRemark(principalForEachInstallment, fixedInterest, serviceFee)));
        }
        return installments;
    }

    private static String generateRemark(Integer principalLoanAmount, double fixedInterest, double serviceFee) {
        return "Principal Installment: ".concat(String.valueOf(principalLoanAmount))
                .concat(", Interest: ").concat(String.valueOf(fixedInterest))
                .concat(", Service Fees: ").concat(String.valueOf(serviceFee));
    }
}
