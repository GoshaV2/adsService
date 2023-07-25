package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import ru.skypro.homework.core.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Schema(description = "Данные для регистрации")
public class RegisterReq {
    @Schema(description = "логин")
    @Min(3)
    @Max(250)
    @Email
    private String username;
    @Schema(description = "пароль")
    @Min(3)
    @Max(250)
    private String password;
    @Schema(description = "Имя")
    @Min(3)
    @Max(250)
    private String firstName;
    @Schema(description = "Фамилия")
    @Min(3)
    @Max(250)
    private String lastName;
    @Schema(description = "Телефон")
    @Min(3)
    @Max(250)
    private String phone;
    @Schema(description = "Роль")
    @NotNull
    private Role role;
}
