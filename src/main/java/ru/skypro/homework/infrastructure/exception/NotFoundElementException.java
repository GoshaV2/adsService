package ru.skypro.homework.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundElementException extends ResponseStatusException {


    public NotFoundElementException(long id, Class<?> type) {
        this(String.format("Entity(%s) with id=%d not be found", type.getName(), id));
    }

    public NotFoundElementException(String message) {
        super(HttpStatus.NOT_FOUND, String.format(message));
    }

    public NotFoundElementException(String format, Object... args) {
        this(String.format(format, args));
    }

    public NotFoundElementException(long id, String type) {
        this(String.format("Entity(%s) with id=%d not be found", type, id));
    }

}