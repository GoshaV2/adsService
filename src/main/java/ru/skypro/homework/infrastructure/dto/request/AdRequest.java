package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Добавление или изменение объявления")
public class AdRequest {
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Цена")
    private long price;
    @Schema(description = "Заголовок")
    private String title;
}
