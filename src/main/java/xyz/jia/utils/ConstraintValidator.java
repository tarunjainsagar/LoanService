package xyz.jia.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.jia.annotation.IConstraintValidator;
import xyz.jia.config.LoanConfiguration;
import xyz.jia.constants.FormatConstants;
import xyz.jia.exception.ConstraintValidationException;
import xyz.jia.model.enums.EnumFrequencyType;
import xyz.jia.model.input.AbstractInput;

import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class ConstraintValidator implements javax.validation.ConstraintValidator<IConstraintValidator, AbstractInput> {

    private final LoanConfiguration loanConfiguration;

    @Autowired
    public ConstraintValidator(LoanConfiguration loanConfiguration) {
        this.loanConfiguration = loanConfiguration;
    }

    @Override
    public void initialize(IConstraintValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(AbstractInput value, ConstraintValidatorContext context) {
        List<String> errorMessages = new ArrayList<>();

        // Check for potential NullPointerException
        if (value == null) {
            throw new ConstraintValidationException(Collections.singletonList("Input is null"));
        }
        if (value.getAmount() == null) {
            errorMessages.add("Missing 'amount'");
        }
        if (value.getDurationType() == null) {
            errorMessages.add("Missing 'durationType'");
        }
        if (value.getDuration() == null) {
            errorMessages.add("Missing 'duration'");
        }
        if (value.getInstallmentFrequency() == null) {
            errorMessages.add("Missing 'installmentFrequency'");
        }
        if (value.getStartDate() == null) {
            errorMessages.add("Missing 'startDate'");
        }

        // If NPE found, throw appropriate error
        if (!errorMessages.isEmpty()) {
            throw new ConstraintValidationException(errorMessages);
        }

        // Check for valid 'amount'
        if (value.getAmount() <= loanConfiguration.getMinAmount()) {
            errorMessages.add("Invalid 'amount'");
        }

        // Check for valid duration based on the frequency type
        switch (value.getDurationType()) {
            case WEEKLY:
                if (value.getDuration() < loanConfiguration.getMinWeeklyDuration()
                        || value.getDuration() > loanConfiguration.getMaxWeeklyDuration()) {
                    errorMessages.add("Duration is invalid for WEEKLY frequency.");
                }
                break;
            case MONTHLY:
                if (value.getDuration() < loanConfiguration.getMinMonthlyDuration()
                        || value.getDuration() > loanConfiguration.getMaxMonthlyDuration()) {
                    errorMessages.add("Duration is invalid for MONTHLY frequency.");
                }
                break;
            default:
                errorMessages.add("Invalid 'durationType'");
        }

        // Check for valid installment frequency based on the frequency type
        /*
         *       Duration        Installment         Result
         *       Week            Month               Not Allowed
         *       Week            Week                Allowed
         *       Month           Month               Allowed
         *       Month           Week                Allowed
         * */
        if (value.getDurationType() == EnumFrequencyType.WEEKLY) {
            if (value.getInstallmentFrequency() == EnumFrequencyType.MONTHLY) {
                errorMessages.add("Installment frequency is invalid for WEEKLY duration.");
            }
        }

        // Check for valid start date
        SimpleDateFormat dateFormat = new SimpleDateFormat(FormatConstants.dateFormat);
        dateFormat.setLenient(false); // Disable leniency for strict date format checking
        try {
            Date startDate = dateFormat.parse(value.getStartDate());
            Date currentDate = new Date();
            if (startDate.before(currentDate)) {
                errorMessages.add("Start date can not be less than current date.");
            }
        } catch (ParseException e) {
            errorMessages.add("Invalid date format. Date format should be in ".concat(FormatConstants.dateFormat));
        }

        // If any validation fails, throw appropriate error
        if (!errorMessages.isEmpty()) {
            throw new ConstraintValidationException(errorMessages);
        }

        // If all validations pass, return true
        return true;
    }
}
