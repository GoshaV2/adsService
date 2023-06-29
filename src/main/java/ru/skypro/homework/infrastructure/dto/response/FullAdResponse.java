package ru.skypro.homework.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Полная информация о объявлении с автором")
public class FullAdResponse {
    /**
     * Для совместимости с фронтом используется статическая ссылка на эндпоинт
     */
    @JsonProperty("image")
    @Schema(description = "Ссылка на аватарку")
    private String imageUrl;
    @JsonProperty("pk")
    @Schema(description = "id объявления")
    private long id;
    @Schema(description = "Цена")
    private long price;
    @Schema(description = "Заголовок")
    private String title;
    @Schema(description = "Телефон автора")
    private String phone;
    @Schema(description = "Почта автора")
    private String email;
    @Schema(description = "Описание автора")
    private String description;
    @Schema(description = "Фамилия автора")
    private String authorLastName;
    @Schema(description = "Имя автора")
    private String authorFirstName;
}
