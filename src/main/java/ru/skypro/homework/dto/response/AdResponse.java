package ru.skypro.homework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdResponse {
    @JsonProperty("author")
    private long userId;
    private List<String> image;
    @JsonProperty("pk")
    private long id;
    private long price;
    private String title;
}
