package ru.skypro.homework.core.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.response.AdResponse;
import ru.skypro.homework.infrastructure.dto.response.AdsListResponse;
import ru.skypro.homework.infrastructure.dto.response.FullAdResponse;

/**
 * Сервис объявлений
 */
public interface AdService {
    Ad getAd(long id);

    /**
     * Получить все объявления
     *
     * @return dto со списком
     */
    AdsListResponse getAllAds();

    /**
     * Добавить объявления
     *
     * @param adRequest     дто данные о объявление
     * @param multipartFile файл с картинкой
     * @param user          автор
     * @return дто с результатом
     */
    AdResponse addAd(AdRequest adRequest, MultipartFile multipartFile, User user);

    /**
     * Получить полное описание объявление с автором
     *
     * @param adId id объвления
     * @return дто с полным описанием
     */
    FullAdResponse getFullAd(long adId);

    /**
     * Удалить объявление
     *
     * @param adId id объявления
     */
    void removeAd(long adId);

    /**
     * Обновить объявление
     *
     * @param adId      id объявление
     * @param adRequest дто с данными
     * @return дто с результатом
     */
    AdResponse updateAds(long adId, AdRequest adRequest);

    /**
     * Получить объявления автора
     *
     * @param authorId id автора
     * @return дто со списком объвлений
     */
    AdsListResponse getUserAds(long authorId);
}
