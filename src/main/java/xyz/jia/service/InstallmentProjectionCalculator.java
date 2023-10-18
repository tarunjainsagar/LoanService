package xyz.jia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.jia.model.enums.EnumFrequencyType;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.model.interfaces.ICalculator;
import xyz.jia.model.response.InstallmentProjectionResponse;
import xyz.jia.model.response.ProjectionReponse;
import xyz.jia.utils.CalculatorUtils;

import java.util.List;

@Component
public class InstallmentProjectionCalculator implements ICalculator {

    @Autowired
    CalculatorUtils calculatorUtils;

    @Override
    public InstallmentProjectionResponse calculate(AbstractInput input) {

        int loanAmount = input.getAmount();
        EnumFrequencyType installmentFrequency = input.getInstallmentFrequency();
        EnumFrequencyType durationType = input.getDurationType();

        int noOfInstallments = calculatorUtils.getNoOfInstallments(input.getDuration(), durationType, installmentFrequency);
        int principalForEachInstallment = calculatorUtils.getPrincipalForEachInstallment(loanAmount, noOfInstallments);
        List<ProjectionReponse> installments = calculatorUtils.getInstallments(input, principalForEachInstallment, noOfInstallments, false);

        InstallmentProjectionResponse ipr = new InstallmentProjectionResponse();
        ipr.setActual_loan_amount(loanAmount);
        ipr.setNo_of_installments(noOfInstallments);
        ipr.setDurationType(durationType);
        ipr.setInstallmentFrequency(installmentFrequency);
        ipr.setProjections(installments);
        return ipr;
    }
}
