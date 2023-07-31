package ru.skypro.homework.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileNotUploadException extends ResponseStatusException {
    public FileNotUploadException(Throwable throwable) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "File not upload.", throwable);
    }
}
