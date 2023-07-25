package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Schema(description = "Данные для аутентификации")
public class LoginReq {
    @Schema(description = "Пароль")
    @Min(3)
    @Max(250)
    private String password;
    @Schema(description = "Логин")
    @Min(3)
    @Max(250)
    @Email
    private String username;

}
