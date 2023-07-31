package ru.skypro.homework.infrastructure.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = {ResponseStatusException.class})
    protected ResponseEntity<Object> handleAmountPatternException(
            ResponseStatusException exception, WebRequest request) {
        log.error(exception.toString());
        return handleExceptionInternal(exception, new Response(exception.getMessage(), exception),
                new HttpHeaders(), exception.getStatus(), request);
    }

    @ExceptionHandler(value
            = {ConstraintViolationException.class})
    public ResponseEntity<Response> handle(ConstraintViolationException exception) {
        Response response = new Response(exception.getMessage(), exception);
        log.error(exception.toString());
        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value
            = {UsernameNotFoundException.class})
    public ResponseEntity<Response> handle(UsernameNotFoundException exception) {
        Response response = new Response(exception.getMessage(), exception);
        log.error(exception.toString());
        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status, @NotNull WebRequest request) {
        StringBuilder errorFieldsMessageBuilder = new StringBuilder();
        errorFieldsMessageBuilder.append("Error fields: [");
        int remainingCount = ex.getFieldErrors().size();
        for (FieldError fieldError : ex.getFieldErrors()) {
            errorFieldsMessageBuilder
                    .append("(")
                    .append(fieldError.getField())
                    .append(" -> ")
                    .append(fieldError.getDefaultMessage())
                    .append(")");
            if (remainingCount > 1) {
                errorFieldsMessageBuilder.append(", ");
            }
            remainingCount--;
        }
        errorFieldsMessageBuilder.append("]");
        log.error(ex.toString());
        return handleExceptionInternal(ex, new Response(errorFieldsMessageBuilder.toString(), ex),
                headers, HttpStatus.BAD_REQUEST, request);
    }

}

