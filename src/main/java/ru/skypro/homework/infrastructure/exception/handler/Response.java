package ru.skypro.homework.infrastructure.exception.handler;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

class Response {
    private final String reason;
    private final LocalDateTime dateTime;
    private final String exceptionName;

    public Response(String reason, Exception e) {
        this.reason = reason;
        this.exceptionName = parseExceptionName(e);
        dateTime = LocalDateTime.now();
    }

    private String parseExceptionName(Exception e) {
        String exceptionClassName = e.getClass().getName();
        int indexLastDot = exceptionClassName.lastIndexOf(".");
        if (indexLastDot == -1) {
            return exceptionClassName;
        }
        return StringUtils.substring(exceptionClassName, indexLastDot + 1);
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getExceptionName() {
        return exceptionName;
    }
}
