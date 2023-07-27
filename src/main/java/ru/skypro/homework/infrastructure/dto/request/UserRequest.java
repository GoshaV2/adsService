package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
@Schema(description = "Данные для изменения пользователя")
public class UserRequest {
    @Schema(description = "Имя")
    @Size(min = 3, max = 250)
    private String firstName;
    @Schema(description = "Фамилия")
    @Size(min = 3, max = 250)
    private String lastName;
    @Schema(description = "Телефон")
    @Size(min = 3, max = 250)
    private String phone;
}
