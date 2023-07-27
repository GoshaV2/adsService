package ru.skypro.homework.infrastructure.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.mapper.UserMapper;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.FileRepository;
import ru.skypro.homework.core.repository.UserRepository;
import ru.skypro.homework.core.service.UserService;
import ru.skypro.homework.infrastructure.dto.request.PasswordRequest;
import ru.skypro.homework.infrastructure.dto.request.UserRequest;
import ru.skypro.homework.infrastructure.dto.response.UserResponse;
import ru.skypro.homework.infrastructure.exception.CredentialsException;
import ru.skypro.homework.infrastructure.exception.FileInputStreamException;
import ru.skypro.homework.infrastructure.facade.AuthenticationFacade;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;
    private final FileRepository fileRepository;
    @Value("${user.image.url}")
    private String userImageUrl;

    @Override
    public UserResponse getUserResponse() {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void changePassword(PasswordRequest passwordRequest) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
            throw new CredentialsException();
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

    @Override
    @Transactional
    public void updateUserImage(MultipartFile file) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        String imageName = getUserImageName(user.getId());
        user.setUserImageUrl(getUserImageUrl(user.getId()));
        userRepository.save(user);
        try {
            fileRepository.addFile(imageName, file.getInputStream());
        } catch (IOException e) {
            throw new FileInputStreamException(e);
        }
    }

    @Override
    public InputStream getUserImage(long userId) {
        return fileRepository.getFile(getUserImageName(userId));
    }

    private String getUserImageName(long userId) {
        return "/image/user/" + userId + ".jpeg";
    }

    private String getUserImageUrl(long userId) {
        return String.format(userImageUrl, userId);
    }
}
