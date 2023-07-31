package ru.skypro.homework.core.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.infrastructure.dto.response.UserResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toUserResponse_whenDataSuccess() {
        User user = getUser();
        UserResponse userResponse = userMapper.toUserResponse(user);

        assertThat(userResponse,
                hasProperty("email", equalTo(user.getEmail())));
        assertThat(userResponse,
                hasProperty("imageUrl", equalTo(user.getUserImageUrl())));
        assertThat(userResponse,
                hasProperty("id", equalTo(user.getId())));
        assertThat(userResponse,
                hasProperty("phone", equalTo(user.getPhone())));
        assertThat(userResponse,
                hasProperty("lastName", equalTo(user.getLastName())));
        assertThat(userResponse,
                hasProperty("firstName", equalTo(user.getFirstName())));
        assertThat(userResponse,
                hasProperty("role", equalTo(user.getRole())));
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .email("email")
                .userImageUrl("urlImage")
                .role(Role.ADMIN)
                .phone("phone")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .lastName("lastName")
                .build();

    }
}