package ru.skypro.homework.core.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.response.AdListResponse;
import ru.skypro.homework.infrastructure.dto.response.AdListResponsePage;
import ru.skypro.homework.infrastructure.dto.response.AdResponse;
import ru.skypro.homework.infrastructure.dto.response.FullAdResponse;

import java.io.InputStream;

/**
 * Сервис объявлений
 */
public interface AdService {
    /**
     * Поиск по ключевому слову, например название или описание
     *
     * @param keyWord      ключевое слово
     * @param page         страница
     * @param countPerPage количество на странице
     * @return дто со списком объявлений и информации о страницах
     */
    AdListResponsePage findAds(String keyWord, int page, int countPerPage);

    /**
     * Получить все объявления
     *
     * @return dto со списком
     */
    AdListResponse getAllAds();

    /**
     * Добавить объявление
     *
     * @param adRequest     дто данные о объявление
     * @param multipartFile файл с картинкой
     * @return дто с результатом
     */
    AdResponse addAd(AdRequest adRequest, MultipartFile multipartFile);

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
    AdListResponse getUserAds(long authorId);

    InputStream getAdImage(long adId);
}
