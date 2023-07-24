package ru.skypro.homework.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Возращаемое объявление")
public class AdResponse {
    @Schema(description = "id автора")
    @JsonProperty("author")
    private long userId;
    @Schema(description = "Ссылка на картинку")
    @JsonProperty("image")
    private String imageUrl;
    @JsonProperty("pk")
    @Schema(description = "id объявления")
    private long id;
    @Schema(description = "Цена")
    private long price;
    @Schema(description = "Заголовок")
    private String title;
}
