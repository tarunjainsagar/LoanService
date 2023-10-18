package xyz.jia.service;

import org.springframework.stereotype.Component;
import xyz.jia.model.enums.EnumFrequencyType;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.interfaces.ICalculator;
import xyz.jia.model.response.FeeProjectionResponse;
import xyz.jia.model.response.ProjectionReponse;
import xyz.jia.utils.CalculatorUtils;

import java.util.List;

@Component
public class FeeProjectionCalculator implements ICalculator {

    @Override
    public FeeProjectionResponse calculate(AbstractInput input) {

        int loanAmount = input.getAmount();
        EnumFrequencyType installmentFrequency = input.getInstallmentFrequency();
        EnumFrequencyType durationType = input.getDurationType();

        int noOfInstallments = CalculatorUtils.getNoOfInstallments(input.getDuration(), durationType, installmentFrequency);
        int principalForEachInstallment = CalculatorUtils.getPrincipalForEachInstallment(loanAmount, noOfInstallments);
        List<ProjectionReponse> installments = CalculatorUtils.getInstallments(input, principalForEachInstallment, noOfInstallments, true);

        FeeProjectionResponse fpr = new FeeProjectionResponse();
        fpr.setActual_loan_amount(loanAmount);
        fpr.setNo_of_installments(noOfInstallments);
        fpr.setDurationType(durationType);
        fpr.setInstallmentFrequency(installmentFrequency);
        fpr.setProjections(installments);
        return fpr;
    }
}
