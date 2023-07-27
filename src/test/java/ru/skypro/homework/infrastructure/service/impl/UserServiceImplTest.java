package ru.skypro.homework.infrastructure.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.mapper.UserMapper;
import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.FileRepository;
import ru.skypro.homework.core.repository.UserRepository;
import ru.skypro.homework.infrastructure.dto.request.PasswordRequest;
import ru.skypro.homework.infrastructure.exception.CredentialsException;
import ru.skypro.homework.infrastructure.facade.AuthenticationFacade;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private FileRepository fileRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(userService, "userImageUrl", "/users/image/%d");
    }

    @Test
    void changePassword_whenPasswordEqualToCurrent_thenSuccessChangePassword() {
        User user = getDefaultUserWithoutImage();
        PasswordRequest passwordRequest = new PasswordRequest();
        passwordRequest.setCurrentPassword("Password");
        passwordRequest.setNewPassword("PasswordNew");
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(passwordEncoder.matches("Password", "Password")).thenReturn(true);
        when(passwordEncoder.encode("PasswordNew")).thenReturn("PasswordNew");
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(user);

        assertDoesNotThrow(() -> userService.changePassword(passwordRequest));
        assertEquals(passwordRequest.getNewPassword(), userArgumentCaptor.getValue().getPassword());
    }

    @Test
    void changePassword_whenPasswordNotEqualToCurrent_thenThrowException() {
        User user = getDefaultUserWithoutImage();
        PasswordRequest passwordRequest = new PasswordRequest();
        passwordRequest.setCurrentPassword("PasswordWrong");
        passwordRequest.setNewPassword("PasswordNew");
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(passwordEncoder.matches("PasswordWrong", "Password")).thenReturn(false);

        assertThrows(CredentialsException.class, () -> userService.changePassword(passwordRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserImage_whenFileSuccessSave_thenUserSetImageUrl() throws IOException {
        User user = getDefaultUserWithoutImage();
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(user);

        userService.updateUserImage(multipartFile);

        verify(userRepository).save(user);
        verify(fileRepository, only()).addFile(any(), any());
        assertEquals(userArgumentCaptor.getValue().getUserImageUrl(), "/users/image/1");
    }

    private User getDefaultUserWithoutImage() {
        return User.builder()
                .id(1L)
                .email("test@mail.ru")
                .firstName("name")
                .lastName("surname")
                .password("Password")
                .phone("phone")
                .role(Role.USER)
                .build();
    }
}