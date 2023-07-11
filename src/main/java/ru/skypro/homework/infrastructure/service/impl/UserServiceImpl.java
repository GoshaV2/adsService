package ru.skypro.homework.infrastructure.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.core.mapper.UserMapper;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.UserRepository;
import ru.skypro.homework.core.service.UserService;
import ru.skypro.homework.infrastructure.dto.request.PasswordRequest;
import ru.skypro.homework.infrastructure.dto.request.UserRequest;
import ru.skypro.homework.infrastructure.dto.response.UserResponse;
import ru.skypro.homework.infrastructure.facade.AuthenticationFacade;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public UserResponse getUserResponse() {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        return userMapper.toUserResponse(user);
    }

    @Override
    public void changePassword(PasswordRequest passwordRequest) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
            //todo throw exception
        }
        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        user.setPhone(userRequest.getPhone());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
