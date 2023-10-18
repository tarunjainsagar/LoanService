package xyz.jia.exception;

import java.util.List;

public class ConstraintValidationException extends RuntimeException {
    private final List<String> errorMessages;

    public ConstraintValidationException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}

