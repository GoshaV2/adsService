package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Данные для аутентификации")
public class LoginReq {
    @Schema(description = "Пароль")
    private String password;
    @Schema(description = "Логин")
    private String username;

}
