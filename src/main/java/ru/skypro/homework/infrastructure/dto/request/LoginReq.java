package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Schema(description = "Данные для аутентификации")
public class LoginReq {
    @Schema(description = "Пароль")
    @Size(min = 3, max = 250)
    private String password;
    @Schema(description = "Логин")
    @Size(min = 3, max = 250)
    @Email
    private String username;

}
