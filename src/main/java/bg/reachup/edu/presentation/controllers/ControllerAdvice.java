package bg.reachup.edu.presentation.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ValidationViolationResponse onConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> errors = new ArrayList<>(constraintViolations.size());
        constraintViolations
                .stream()
                .map(ConstraintViolation::getMessage)
                .forEach(errors::add);
        return new ValidationViolationResponse(LocalDateTime.now(ZoneId.systemDefault()), errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ValidationViolationResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size());
        fieldErrors
                .stream()
                .map(FieldError::getDefaultMessage)
                .forEach(errors::add);
        return new ValidationViolationResponse(LocalDateTime.now(ZoneId.systemDefault()), errors);
    }
}

record ValidationViolationResponse(

        LocalDateTime timestamp,
        int status,
        String message,

        List<String> errors
) {
    ValidationViolationResponse(LocalDateTime timestamp, List<String> errors) {
        this(timestamp, HttpStatus.BAD_REQUEST.value(), "Invalid data found!", errors);
    }
}