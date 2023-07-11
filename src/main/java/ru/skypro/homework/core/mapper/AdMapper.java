package ru.skypro.homework.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.response.AdListResponse;
import ru.skypro.homework.infrastructure.dto.response.AdListResponsePage;
import ru.skypro.homework.infrastructure.dto.response.AdResponse;
import ru.skypro.homework.infrastructure.dto.response.FullAdResponse;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class AdMapper {
    @Mapping(target = "phone", source = "ad.author.phone")
    @Mapping(target = "email", source = "ad.author.email")
    @Mapping(target = "authorLastName", source = "ad.author.lastName")
    @Mapping(target = "authorFirstName", source = "ad.author.firstName")
    public abstract FullAdResponse toFullAdResponse(Ad ad, String imageUrl);

    @Mapping(target = "userId", source = "ad.author.id")
    public abstract AdResponse toAdResponse(Ad ad);

    public AdListResponse toAdListResponse(List<Ad> adList) {
        AdListResponse adListResponse = new AdListResponse();
        adListResponse.setAdResponseList(adList.stream()
                .map(this::toAdResponse).collect(Collectors.toList()));
        adListResponse.setCount(adList.size());
        return adListResponse;
    }

    public abstract Ad fromAdRequest(AdRequest adRequest);

    public AdListResponsePage toAdListResponsePage(List<Ad> adList, int page, int totalPage, long totalElements) {
        AdListResponsePage adListResponsePage = new AdListResponsePage();
        adListResponsePage.setAdResponseList(adList.stream()
                .map(this::toAdResponse).collect(Collectors.toList()));
        adListResponsePage.setTotalPage(totalPage);
        adListResponsePage.setPage(page);
        adListResponsePage.setTotalElements(totalElements);
        adListResponsePage.setCount(adList.size());
        return adListResponsePage;
    }
}
