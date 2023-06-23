package ru.skypro.homework.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class AdRequest {
    @JsonProperty("author")
    private long userId;
    private List<String> image;
    @JsonProperty("pk")
    private long id;
    private long price;
    private String title;
}
