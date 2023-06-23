package ru.skypro.homework.dto.request;

import lombok.Getter;

@Getter
public class PasswordRequest {
    private String currentPassword;
    private String newPassword;
}
