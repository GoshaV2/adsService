package ru.skypro.homework.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Описание")
public class UserResponse {
    @Schema(description = "Почта")
    private String email;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "id пользователя")
    private long id;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Телефон")
    private String phone;
    @Schema(description = "Дата регистрации")
    private String regDate;
    @Schema(description = "Город")
    private String city;
    @JsonProperty("image")
    private String imageUrl;
}
