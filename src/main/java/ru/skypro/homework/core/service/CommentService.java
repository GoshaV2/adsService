package ru.skypro.homework.core.service;

import ru.skypro.homework.core.model.User;
import ru.skypro.homework.infrastructure.dto.request.CommentRequest;
import ru.skypro.homework.infrastructure.dto.response.CommentListResponse;
import ru.skypro.homework.infrastructure.dto.response.CommentResponse;

/**
 * Сервис комментариев
 */
public interface CommentService {
    /**
     * Получить список комментариев объявления
     *
     * @param adId id объявления
     * @return дто со списком комментариев
     */
    CommentListResponse getCommentOfAd(long adId);

    /**
     * Добавление комментария
     *
     * @param adId           id объявления
     * @param commentRequest дто с данными комментария
     * @param user           автор комментария
     * @return дто с данными о комментария
     */
    CommentResponse addComment(long adId, CommentRequest commentRequest, User user);

    /**
     * Получить комментарий по id
     *
     * @param adId      id объявления
     * @param commentId id комментария
     * @return дто с данными о комментария
     */
    CommentResponse getCommentResponse(long adId, long commentId);

    /**
     * Удалить комментарий
     *
     * @param adId      id объявления
     * @param commentId id комментария
     */
    void removeComment(long adId, long commentId);

    /**
     * Обновить комментарий
     *
     * @param adId           id объявления
     * @param commentId      id комментария
     * @param commentRequest дто с данными комментария для обновления
     * @return дто с данными о комментарии
     */
    CommentResponse updateComment(long adId, long commentId, CommentRequest commentRequest);
}
