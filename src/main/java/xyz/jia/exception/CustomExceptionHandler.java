package xyz.jia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static xyz.jia.constants.ErrorConstants.*;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintValidationException.class)
    public final ResponseEntity<Object> handleValidationException(ConstraintValidationException ex) {
        List<String> errorMessages = ex.getErrorMessages();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", ERROR_CODE_BAD_REQUEST);
        responseBody.put("message", INVALID_INPUT);
        responseBody.put("errors", errorMessages);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidApiException.class)
    public final ResponseEntity<Object> handleValidationException(InvalidApiException ex) {
        String errorMessage = ex.getMessage();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", ERROR_CODE_BAD_REQUEST);
        responseBody.put("message", INVALID_API);
        responseBody.put("errors", errorMessage);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

}
