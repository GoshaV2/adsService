package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Schema(description = "Данные для смены пароля")
public class PasswordRequest {
    @Schema(description = "Текущий пароль")
    @Min(3)
    @Max(250)
    private String currentPassword;
    @Schema(description = "Новый пароль")
    @Min(3)
    @Max(250)
    private String newPassword;
}
