package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Schema(description = "Описание")
public class CommentRequest {
    @Schema(description = "Текст комментария")
    @Min(3)
    @Max(250)
    private String text;
}
