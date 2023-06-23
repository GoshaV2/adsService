package ru.skypro.homework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    @JsonProperty("author")
    private long userId;
    @JsonProperty("createdAt")
    private LocalDateTime createdDate;
    @JsonProperty("pk")
    private long id;
    private String text;
}
