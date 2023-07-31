package ru.skypro.homework.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CredentialsException extends ResponseStatusException {
    public CredentialsException() {
        super(HttpStatus.BAD_REQUEST, "Old password not equals to new password");
    }
}
