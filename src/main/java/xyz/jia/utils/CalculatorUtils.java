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

    @Autowired
    LoanConfiguration loanConfiguration;

    // todo: not important: can cache result for this method
    public int getNoOfInstallments(int loanDuration, EnumFrequencyType durationType, EnumFrequencyType installmentFrequency) {
        int noOfInstallments = loanDuration;

        if (durationType == EnumFrequencyType.MONTHLY && installmentFrequency == EnumFrequencyType.WEEKLY) {
            // convert months to weeks
            int totalDaysToPayLoan = loanDuration * loanConfiguration.getConstraints().getDaysInMonth();
            double totalWeeks = (double) totalDaysToPayLoan / loanConfiguration.getConstraints().getDaysInWeek();
            noOfInstallments = (int) Math.ceil(totalWeeks);
        }

        return noOfInstallments;
    }

    // todo: not important: can cache result for this method
    public int getPrincipalForEachInstallment(int loanAmount, int noOfInstallments) {
        // principal evenly spread across all installments
        double principalForEachInstallment = (double) loanAmount / noOfInstallments;
        return (int) Math.ceil(principalForEachInstallment);
    }

    public List<ProjectionReponse> getInstallments(AbstractInput input,
                                                          int principalForEachInstallment,
                                                          int noOfInstallments,
                                                          boolean onlyFees) {
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
            double installmentAmount = onlyFees ? fixedInterest : (principalForEachInstallment + fixedInterest);
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
                    .setRemark(generateRemark(principalForEachInstallment, fixedInterest, serviceFee, onlyFees)));
        }
        return installments;
    }

    private String generateRemark(Integer principalLoanAmount, double fixedInterest, double serviceFee, boolean onlyFees) {
        StringBuilder remarkBuilder = new StringBuilder("Interest: ").append(fixedInterest).append(", Service Fees: ").append(serviceFee);
        if (!onlyFees) {
            remarkBuilder.append(", Principal Installment: ").append(principalLoanAmount);
        }
        return remarkBuilder.toString();
    }

}
