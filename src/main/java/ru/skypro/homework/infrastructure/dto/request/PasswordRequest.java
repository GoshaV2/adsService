package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Данные для смены пароля")
public class PasswordRequest {
    @Schema(description = "Текущий пароль")
    private String currentPassword;
    @Schema(description = "Новый пароль")
    private String newPassword;
}
