package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@Schema(description = "Данные для смены пароля")
public class PasswordRequest {
    @Schema(description = "Текущий пароль")
    @Size(min = 3, max = 250)
    private String currentPassword;
    @Schema(description = "Новый пароль")
    @Size(min = 3, max = 250)
    private String newPassword;
}
