package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Добавление или изменение объявления")
public class AdRequest {
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Цена")
    private int price;
    @Schema(description = "Заголовок")
    private String title;
}
