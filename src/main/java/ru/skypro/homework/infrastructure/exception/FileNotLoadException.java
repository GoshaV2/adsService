package ru.skypro.homework.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileNotLoadException extends ResponseStatusException {
    public FileNotLoadException(Throwable throwable) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "File not load.", throwable);
    }


}
