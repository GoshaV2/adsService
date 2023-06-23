package ru.skypro.homework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdsListResponse {
    @JsonProperty("results")
    private List<AdResponse> adResponseList;
    private int count;
}

