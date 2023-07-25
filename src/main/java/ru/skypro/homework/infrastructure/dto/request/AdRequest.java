package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Добавление или изменение объявления")
public class AdRequest {
    @Schema(description = "Описание")
    @Min(3)
    @Max(250)
    private String description;
    @Schema(description = "Цена")
    @Min(3)
    @Max(250)
    private long price;
    @Schema(description = "Заголовок")
    @Min(3)
    @Max(250)
    private String title;
}
