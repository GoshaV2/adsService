package ru.skypro.homework.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileInputStreamException extends ResponseStatusException {
    public FileInputStreamException(Throwable throwable) {
        super(HttpStatus.BAD_REQUEST, "File's input stream can't be read", throwable);
    }
}
