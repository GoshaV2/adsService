package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Добавление или изменение объявления")
public class AdRequest {
    @Schema(description = "Описание")
    @Size(min = 3, max = 250)
    private String description;
    @Schema(description = "Цена")
    @Min(3)
    @Max(100000)
    private long price;
    @Schema(description = "Заголовок")
    @Size(min = 3, max = 250)
    private String title;
}
