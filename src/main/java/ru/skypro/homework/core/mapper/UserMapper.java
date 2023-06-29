package ru.skypro.homework.core.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.infrastructure.dto.request.UserRequest;
import ru.skypro.homework.infrastructure.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserRequest(UserRequest userRequest);

    UserResponse toUserResponse(User user);
}
