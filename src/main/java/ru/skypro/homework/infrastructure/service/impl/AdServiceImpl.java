package ru.skypro.homework.infrastructure.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.mapper.AdMapper;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.AdRepository;
import ru.skypro.homework.core.repository.FileRepository;
import ru.skypro.homework.core.service.AdService;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.response.AdListResponse;
import ru.skypro.homework.infrastructure.dto.response.AdListResponsePage;
import ru.skypro.homework.infrastructure.dto.response.AdResponse;
import ru.skypro.homework.infrastructure.dto.response.FullAdResponse;
import ru.skypro.homework.infrastructure.facade.AuthenticationFacade;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final AuthenticationFacade authenticationFacade;
    private final AdMapper adMapper;
    private final FileRepository fileRepository;
    @Value("${ad.image.url}")
    private String adImageUrl;

    private Ad getAdWithRole(long id) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        Role role = user.getRole();
        //todo добавить ошибку
        if (role == Role.ADMIN) {
            return adRepository.findById(id).orElseThrow();
        }
        return adRepository.findByIdAndAuthorId(id, user.getId()).orElseThrow();
    }

    private Ad getAd(long id){
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
        return adMapper.toAdListResponsePage(adsPage.getContent(), page, adsPage.getTotalPages(),
                adsPage.getTotalElements(), getAdImageMap(adsPage.getContent()));
    }

    @Override
    public AdListResponse getAllAds() {
        List<Ad> adList = adRepository.findAll();
        return adMapper.toAdListResponse(adList, getAdImageMap(adList));
    }

    @Override
    @Transactional
    public AdResponse addAd(AdRequest adRequest, MultipartFile multipartFile) {
        Ad ad = adMapper.fromAdRequest(adRequest);
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        ad.setAuthor(user);
        adRepository.save(ad);
        String fileName = getAdImageName(ad.getId());
        try {
            fileRepository.addFile(fileName, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adMapper.toAdResponse(ad, getAdImageUrl(ad.getId()));
    }

    @Override
    @Transactional
    public FullAdResponse getFullAd(long adId) {
        Ad ad =getAd(adId);
        return adMapper.toFullAdResponse(ad, getAdImageUrl(adId));
    }

    @Override
    public void removeAd(long adId) {
        Ad ad = getAdWithRole(adId);
        adRepository.delete(ad);
    }

    @Override
    public AdResponse updateAds(long adId, AdRequest adRequest) {
        Ad ad = getAdWithRole(adId);
        ad.setTitle(adRequest.getTitle());
        ad.setPrice(adRequest.getPrice());
        ad.setDescription(adRequest.getDescription());
        adRepository.save(ad);
        return adMapper.toAdResponse(ad, getAdImageUrl(adId));
    }

    @Override
    public AdListResponse getUserAds(long authorId) {
        List<Ad> adsOfUser = adRepository.findAllByAuthorId(authorId);
        return adMapper.toAdListResponse(adsOfUser, getAdImageMap(adsOfUser));
    }

    @Override
    public InputStream getAdImage(long adId) {
        return fileRepository.getFile(getAdImageName(adId));
    }

    private String getAdImageName(long adId) {
        return String.format("ad/%d.jpeg", adId);
    }

    private String getAdImageUrl(long adId) {
        return String.format(adImageUrl, adId);
    }

    private Map<Long, String> getAdImageMap(List<Ad> adList) {
        return adList.stream().collect(Collectors.toMap(Ad::getId,
                ad -> getAdImageUrl(ad.getId())));
    }
}
