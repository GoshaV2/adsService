package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Schema(description = "Данные для изменения пользователя")
public class UserRequest {
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
}
