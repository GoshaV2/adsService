package ru.skypro.homework.infrastructure.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.mapper.AdMapper;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.AdRepository;
import ru.skypro.homework.core.service.AdService;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.response.AdListResponse;
import ru.skypro.homework.infrastructure.dto.response.AdListResponsePage;
import ru.skypro.homework.infrastructure.dto.response.AdResponse;
import ru.skypro.homework.infrastructure.dto.response.FullAdResponse;
import ru.skypro.homework.infrastructure.facade.AuthenticationFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final AuthenticationFacade authenticationFacade;
    private final AdMapper adMapper;

    @Override
    public Ad getAd(long id) {
        //todo добавить ошибку
        Ad ad = adRepository.getReferenceById(1L);
        long i = ad.getId();
        return adRepository.findById(id).orElseThrow();
    }

    @Override
    public AdListResponsePage findAds(String keyWord, int page, int countPerPage) {
        if (countPerPage <= 0) {
            countPerPage = 50;
        }
        countPerPage = Math.min(500, countPerPage);
        Pageable pageable = PageRequest.of(page, countPerPage);
        Page<Ad> adsPage = adRepository.findAllByTitleContainsOrDescriptionContains(keyWord, keyWord, pageable);
        return adMapper.toAdListResponsePage(adsPage.getContent(), page, adsPage.getTotalPages(), adsPage.getTotalElements());
    }

    @Override
    public AdListResponse getAllAds() {
        return adMapper.toAdListResponse(adRepository.findAll());
    }

    @Override
    public AdResponse addAd(AdRequest adRequest, MultipartFile multipartFile) {
        Ad ad = adMapper.fromAdRequest(adRequest);
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        ad.setAuthor(user);
        adRepository.save(ad);
        return adMapper.toAdResponse(ad);
    }

    @Override
    @Transactional
    public FullAdResponse getFullAd(long adId) {
        Ad ad = getAd(adId);
        return adMapper.toFullAdResponse(ad, "test");
    }

    @Override
    public void removeAd(long adId) {
        adRepository.deleteById(adId);
    }

    @Override
    public AdResponse updateAds(long adId, AdRequest adRequest) {
        Ad ad = getAd(adId);
        ad.setTitle(adRequest.getTitle());
        ad.setPrice(adRequest.getPrice());
        ad.setDescription(adRequest.getDescription());
        adRepository.save(ad);
        return adMapper.toAdResponse(ad);
    }

    @Override
    public AdListResponse getUserAds(long authorId) {
        List<Ad> adsOfUser = adRepository.findAllByAuthorId(authorId);
        return adMapper.toAdListResponse(adsOfUser);
    }
}
