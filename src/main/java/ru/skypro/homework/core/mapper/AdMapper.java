package ru.skypro.homework.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.response.AdResponse;
import ru.skypro.homework.infrastructure.dto.response.AdsListResponse;
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

    public abstract AdResponse toAdResponse(Ad ad);

    public AdsListResponse toAdsListResponse(List<Ad> adList) {
        AdsListResponse adsListResponse = new AdsListResponse();
        adsListResponse.setAdResponseList(adList.stream()
                .map(this::toAdResponse).collect(Collectors.toList()));
        adsListResponse.setCount(adList.size());
        return adsListResponse;
    }

    public abstract Ad fromAdRequest(AdRequest adRequest);
}
