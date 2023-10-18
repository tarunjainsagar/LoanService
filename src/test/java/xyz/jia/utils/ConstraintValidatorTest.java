package xyz.jia.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.jia.config.ConstraintsConfiguration;
import xyz.jia.exception.ConstraintValidationException;
import xyz.jia.model.enums.EnumFrequencyType;
import xyz.jia.model.input.FeeProjectionInput;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ConstraintValidatorTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    private ConstraintValidator validator;

    @Before
    public void setUp() {
        ConstraintsConfiguration configuration = new ConstraintsConfiguration();
        configuration.setMinAmount(10);
        configuration.setMinWeeklyDuration(1);
        configuration.setMaxWeeklyDuration(10);
        configuration.setMinMonthlyDuration(1);
        configuration.setMaxMonthlyDuration(12);

        validator = new ConstraintValidator(configuration);
    }

    @Test
    public void testIsValidWithValidInput() {
        FeeProjectionInput validInput = new FeeProjectionInput();
        validInput.setAmount(15);
        validInput.setDurationType(EnumFrequencyType.WEEKLY);
        validInput.setDuration(5);
        validInput.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        validInput.setStartDate("2023-10-20");

        assertTrue(validator.isValid(validInput, constraintValidatorContext));
    }

    @Test(expected = ConstraintValidationException.class)
    public void testIsValidWithNullInput() {
        validator.isValid(null, constraintValidatorContext);
    }

    @Test(expected = ConstraintValidationException.class)
    public void testIsValidWithMissingAmount() {
        FeeProjectionInput invalidInput = new FeeProjectionInput();
        invalidInput.setDurationType(EnumFrequencyType.WEEKLY);
        invalidInput.setDuration(5);
        invalidInput.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        invalidInput.setStartDate("2023-10-20");

        validator.isValid(invalidInput, constraintValidatorContext);
    }

    @Test(expected = ConstraintValidationException.class)
    public void testIsValidWithInvalidAmount() {
        FeeProjectionInput invalidInput = new FeeProjectionInput();
        invalidInput.setAmount(5); // Below the configured minimum amount for weekly loan
        invalidInput.setDurationType(EnumFrequencyType.WEEKLY);
        invalidInput.setDuration(5);
        invalidInput.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        invalidInput.setStartDate("2023-10-20");

        validator.isValid(invalidInput, constraintValidatorContext);
    }

    @Test(expected = ConstraintValidationException.class)
    public void testIsValidWithMissingDurationType() {
        FeeProjectionInput invalidInput = new FeeProjectionInput();
        invalidInput.setAmount(15);
        invalidInput.setDuration(5);
        invalidInput.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        invalidInput.setStartDate("2023-10-20");

        validator.isValid(invalidInput, constraintValidatorContext);
    }

    @Test(expected = ConstraintValidationException.class)
    public void testIsValidWithInvalidDurationForWeekly() {
        FeeProjectionInput invalidInput = new FeeProjectionInput();
        invalidInput.setAmount(15);
        invalidInput.setDurationType(EnumFrequencyType.WEEKLY);
        invalidInput.setDuration(20); // Exceeds the configured maximum allowed duration for weekly loan
        invalidInput.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        invalidInput.setStartDate("2023-10-20");

        validator.isValid(invalidInput, constraintValidatorContext);
    }

    @Test(expected = ConstraintValidationException.class)
    public void testIsValidWithInvalidDurationForMonthly() {
        FeeProjectionInput invalidInput = new FeeProjectionInput();
        invalidInput.setAmount(15);
        invalidInput.setDurationType(EnumFrequencyType.MONTHLY);
        invalidInput.setDuration(0); // Below the configured minimum allowed duration for weekly loan
        invalidInput.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        invalidInput.setStartDate("2023-10-20");

        validator.isValid(invalidInput, constraintValidatorContext);
    }

    @Test(expected = ConstraintValidationException.class)
    public void testIsValidWithInvalidInstallmentFrequency() {
        FeeProjectionInput invalidInput = new FeeProjectionInput();
        invalidInput.setAmount(15);
        invalidInput.setDurationType(EnumFrequencyType.WEEKLY);
        invalidInput.setDuration(5);
        invalidInput.setInstallmentFrequency(EnumFrequencyType.MONTHLY); // Invalid for WEEKLY duration
        invalidInput.setStartDate("2023-10-20");

        validator.isValid(invalidInput, constraintValidatorContext);
    }

    @Test(expected = ConstraintValidationException.class)
    public void testIsValidWithInvalidStartDate() {
        FeeProjectionInput invalidInput = new FeeProjectionInput();
        invalidInput.setAmount(15);
        invalidInput.setDurationType(EnumFrequencyType.WEEKLY);
        invalidInput.setDuration(5);
        invalidInput.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        invalidInput.setStartDate("2020-01-01"); // Date is in the past

        validator.isValid(invalidInput, constraintValidatorContext);
    }

    @Test(expected = ConstraintValidationException.class)
    public void testIsValidWithInvalidDateFormat() {
        FeeProjectionInput invalidInput = new FeeProjectionInput();
        invalidInput.setAmount(15);
        invalidInput.setDurationType(EnumFrequencyType.WEEKLY);
        invalidInput.setDuration(5);
        invalidInput.setInstallmentFrequency(EnumFrequencyType.WEEKLY);
        invalidInput.setStartDate("invalid-date-format"); // Invalid date format

        validator.isValid(invalidInput, constraintValidatorContext);
    }
}
