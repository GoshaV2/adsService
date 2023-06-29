package ru.skypro.homework.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Список комментариев")
public class CommentListResponse {
    @Schema(description = "Количество")
    private int count;
    @JsonProperty("results")
    @Schema(description = "Комментарии")
    private List<CommentResponse> commentResponseList;
}
