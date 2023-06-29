package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import ru.skypro.homework.core.model.Role;

@Getter
@Schema(description = "Данные для регистрации")
public class RegisterReq {
    @Schema(description = "логин")
    private String username;
    @Schema(description = "пароль")
    private String password;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Телефон")
    private String phone;
    @Schema(description = "Роль")
    private Role role;
}
