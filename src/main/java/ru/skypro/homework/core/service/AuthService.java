package ru.skypro.homework.core.service;

import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.infrastructure.dto.request.RegisterReq;

/**
 * Сервис аутентификации
 * Для простоты реализована простая реализация.
 */
public interface AuthService {
    /**
     * Аутенификация
     *
     * @param userName логин пользователя
     * @param password пароль пользователя
     * @return флаг прошла ли аутентификация или нет.
     */
    boolean login(String userName, String password);

    /**
     * Регистрация
     *
     * @param registerReq дто с данными пользователя
     * @param role        роль
     * @return флаг прошла ли регистрация или нет.
     */
    boolean register(RegisterReq registerReq, Role role);
}
