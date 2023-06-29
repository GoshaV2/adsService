package ru.skypro.homework.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Описание")
public class CommentRequest {
    @Schema(description = "Текст комментария")
    private String text;
}
