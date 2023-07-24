package ru.skypro.homework.core.mapper;


import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.response.AdListResponse;
import ru.skypro.homework.infrastructure.dto.response.AdListResponsePage;
import ru.skypro.homework.infrastructure.dto.response.AdResponse;
import ru.skypro.homework.infrastructure.dto.response.FullAdResponse;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AdMapperTest {
    private AdMapper adMapper = Mappers.getMapper(AdMapper.class);

    @Test
    void toFullAdResponse_whenAllDataSuccess() {
        Ad ad = getAd();
        String imageUrl = "imageTestUrl";
        FullAdResponse fullAdResponse = adMapper.toFullAdResponse(ad, imageUrl);

        assertThat(fullAdResponse,
                hasProperty("id", equalTo(ad.getId())));
        assertThat(fullAdResponse,
                hasProperty("imageUrl", equalTo(imageUrl)));
        assertThat(fullAdResponse,
                hasProperty("price", equalTo(ad.getPrice())));
        assertThat(fullAdResponse,
                hasProperty("title", equalTo(ad.getTitle())));
        assertThat(fullAdResponse,
                hasProperty("phone", equalTo(ad.getAuthor().getPhone())));
        assertThat(fullAdResponse,
                hasProperty("email", equalTo(ad.getAuthor().getEmail())));
        assertThat(fullAdResponse,
                hasProperty("description", equalTo(ad.getDescription())));
        assertThat(fullAdResponse,
                hasProperty("authorLastName", equalTo(ad.getAuthor().getLastName())));
        assertThat(fullAdResponse,
                hasProperty("authorFirstName", equalTo(ad.getAuthor().getFirstName())));
    }

    /*@Test
    void toAdResponse_whenAllDataSuccess() {
        Ad ad = getAd();
        AdResponse adResponse = adMapper.toAdResponse(ad);

        assertThat(adResponse,
                hasProperty("userId", equalTo(ad.getAuthor().getId())));
        assertThat(adResponse,
                hasProperty("id", equalTo(ad.getId())));
        assertThat(adResponse,
                hasProperty("price", equalTo(ad.getPrice())));
        assertThat(adResponse,
                hasProperty("title", equalTo(ad.getTitle())));
    }

    @Test
    void toAdsListResponse_whenAllDataSuccess() {
        List<Ad> adList = getAdList();
        AdListResponse adListResponse = adMapper.toAdListResponse(adList);

        assertThat(adListResponse,
                hasProperty("count", equalTo(adList.size())));
        assertThat(adListResponse,
                hasProperty("adResponseList"));
        assertThat(adListResponse.getAdResponseList(), hasSize(adList.size()));
    }

    @Test
    void toAdListResponse_whenAllDataSuccess() {
        List<Ad> adList = getAdList();
        int page = 0;
        int totalPage = 1;
        long totalElements = adList.size();
        AdListResponsePage adListResponsePage = adMapper.toAdListResponsePage(adList, page, totalPage, totalElements);

        assertThat(adListResponsePage,
                hasProperty("count", equalTo(adList.size())));
        assertThat(adListResponsePage,
                hasProperty("adResponseList", hasSize(adList.size())));
        assertThat(adListResponsePage,
                hasProperty("count", equalTo(adList.size())));
        assertThat(adListResponsePage,
                hasProperty("page", equalTo(page)));
        assertThat(adListResponsePage,
                hasProperty("totalPage", equalTo(totalPage)));
        assertThat(adListResponsePage,
                hasProperty("totalElements", equalTo(totalElements)));
        assertThat(adListResponsePage.getAdResponseList(), hasSize(adList.size()));
    }*/

    @Test
    void fromAdRequest_whenAllDataSuccess() {
        AdRequest adRequest = getAdRequest();
        Ad ad = adMapper.fromAdRequest(adRequest);

        assertThat(ad,
                hasProperty("id", nullValue()));
        assertThat(ad,
                hasProperty("description", equalTo(adRequest.getDescription())));
        assertThat(ad,
                hasProperty("title", equalTo(adRequest.getTitle())));
        assertThat(ad,
                hasProperty("price", equalTo(adRequest.getPrice())));
    }

    private Ad getAd() {
        User user = User.builder()
                .id(1L)
                .email("test@mail.ru")
                .phone("+777777777")
                .role(Role.USER)
                .city("Moscow")
                .firstName("name")
                .lastName("surname")
                .password("secret")
                .build();
        return Ad.builder()
                .id(1L)
                .author(user)
                .description("description")
                .title("title")
                .price(1000)
                .build();
    }

    private List<Ad> getAdList() {
        return List.of(getAd(), getAd());
    }

    private AdRequest getAdRequest() {
        return AdRequest.builder()
                .description("description")
                .price(1000)
                .title("title")
                .build();
    }
}