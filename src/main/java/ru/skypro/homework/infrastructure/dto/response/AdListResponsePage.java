package ru.skypro.homework.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Список объявлений с пагинацией")
public class AdListResponsePage {
    @JsonProperty("results")
    @Schema(description = "Объявления")
    private List<AdResponse> adResponseList;
    @Schema(description = "Количество")
    private int count;
    @Schema(description = "Номер страницы")
    private int page;
    @Schema(description = "Общее количество страниц")
    private int totalPage;
    @Schema(description = "Общее количество элементов")
    private long totalElements;
}
