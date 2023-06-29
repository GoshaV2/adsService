package ru.skypro.homework.core.service;

import ru.skypro.homework.core.model.User;
import ru.skypro.homework.infrastructure.dto.request.PasswordRequest;
import ru.skypro.homework.infrastructure.dto.request.UserRequest;
import ru.skypro.homework.infrastructure.dto.response.UserResponse;

/**
 * Сервис пользователя
 */
public interface UserService {
    /**
     * Получить дто с данными о пользователе
     *
     * @param user пользователь по которому нужно получить данные
     * @return дто с данными о пользователе
     */
    UserResponse getUserResponse(User user);

    /**
     * Сменить пароль пользователя. Реализована самая простая реализация.
     *
     * @param passwordRequest данные для обновления пароля
     * @param user            пользователь, у которого меняется пароль
     */
    void changePassword(PasswordRequest passwordRequest, User user);

    /**
     * Обновить данные о пользователе
     *
     * @param user        пользователь у которого меняем данные
     * @param userRequest дто с данными о пользователе для изменения
     * @return дто с данными о пользователе
     */
    UserResponse updateUser(User user, UserRequest userRequest);
}
