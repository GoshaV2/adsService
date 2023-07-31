package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import ru.skypro.homework.core.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Schema(description = "Данные для регистрации")
public class RegisterReq {
    @Schema(description = "логин")
    @Size(min = 3, max = 250)
    @Email
    private String username;
    @Schema(description = "пароль")
    @Size(min = 3, max = 250)
    private String password;
    @Schema(description = "Имя")
    @Size(min = 3, max = 250)
    private String firstName;
    @Schema(description = "Фамилия")
    @Size(min = 3, max = 250)
    private String lastName;
    @Schema(description = "Телефон")
    @Size(min = 3, max = 250)
    private String phone;
    @Schema(description = "Роль")
    @NotNull
    private Role role;
}
