package bg.reachup.edu.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.StringJoiner;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String onConstraintViolationException(ConstraintViolationException e) {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            stringJoiner.add(String.format(
                    "%s -> %s",
                    constraintViolation.getPropertyPath(),
                    constraintViolation.getMessage())
            );
        }
        return stringJoiner.toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        for (FieldError fieldErr : e.getFieldErrors()) {
            stringJoiner.add(String.format(
                    "%s -> %s",
                    fieldErr.getField(),
                    fieldErr.getDefaultMessage())
            );
        }
        return stringJoiner.toString();
    }
}
