package ru.skypro.homework.core.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.infrastructure.dto.request.PasswordRequest;
import ru.skypro.homework.infrastructure.dto.request.UserRequest;
import ru.skypro.homework.infrastructure.dto.response.UserResponse;

import java.io.InputStream;

/**
 * Сервис пользователя
 */
public interface UserService {
    /**
     * Получить дто с данными о текущем пользователе
     *
     * @return дто с данными о пользователе
     */
    UserResponse getUserResponse();

    /**
     * Сменить пароль текущего пользователя. Реализована самая простая реализация.
     *
     * @param passwordRequest данные для обновления пароля
     */
    void changePassword(PasswordRequest passwordRequest);

    /**
     * Обновить данные о  текущем пользователе
     *
     * @param userRequest дто с данными о пользователе для изменения
     * @return дто с данными о пользователе
     */
    UserResponse updateUser(UserRequest userRequest);

    void updateUserImage(MultipartFile file);

    InputStream getUserImage(long userId);
}
