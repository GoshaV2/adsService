package ru.skypro.homework.infrastructure.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.mapper.AdMapper;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.AdRepository;
import ru.skypro.homework.core.service.AdService;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.response.AdResponse;
import ru.skypro.homework.infrastructure.dto.response.AdsListResponse;
import ru.skypro.homework.infrastructure.dto.response.FullAdResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    @Override
    public Ad getAd(long id) {
        //todo добавить ошибку
        return adRepository.findById(id).orElseThrow();
    }

    @Override
    public AdsListResponse getAllAds() {
        return adMapper.toAdsListResponse(adRepository.findAll());
    }

    @Override
    public AdResponse addAd(AdRequest adRequest, MultipartFile multipartFile, User user) {
        Ad ad = adMapper.fromAdRequest(adRequest);
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
    public AdsListResponse getUserAds(long authorId) {
        List<Ad> adsOfUser = adRepository.findAllByAuthorId(authorId);
        return adMapper.toAdsListResponse(adsOfUser);
    }
}
