package ru.skypro.homework.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Комментарий")
public class CommentResponse {
    @JsonProperty("author")
    @Schema(description = "id автора")
    private long userId;
    @JsonProperty("createdAt")
    @Schema(description = "Дата обновления")
    private LocalDateTime createdDate;
    @Schema(description = "Имя автора")
    private String authorFirstName;
    @Schema(description = "Имя автора")
    @JsonProperty("authorImage")
    private String authorImageUrl;
    @JsonProperty("pk")
    @Schema(description = "id комментария")
    private long id;
    @Schema(description = "Текст комментария")
    private String text;
}
