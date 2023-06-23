package ru.skypro.homework.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CommentRequest {
    @JsonProperty("author")
    private long userId;
    @JsonProperty("createdAt")
    private LocalDateTime createdDate;
    @JsonProperty("pk")
    private long id;
    private String text;
}
