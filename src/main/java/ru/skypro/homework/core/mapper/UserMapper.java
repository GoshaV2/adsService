package ru.skypro.homework.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.infrastructure.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "imageUrl", source = "userImageUrl")
    UserResponse toUserResponse(User user);
}
