package ru.skypro.homework.infrastructure.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.UserRepository;
import ru.skypro.homework.core.service.AuthService;
import ru.skypro.homework.infrastructure.dto.request.RegisterReq;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserDetailsService UserDetailsService;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public boolean login(String userName, String password) {
        return encoder.matches(password, UserDetailsService.loadUserByUsername(userName).getPassword());
    }

    @Override
    @Transactional
    public boolean register(RegisterReq registerReq, Role role) {
        if (userRepository.existsByEmail(registerReq.getUsername())) {
            log.error(String.format("User[username=%s] already exist.", registerReq.getUsername()));
            return false;
        }
        User user = User.builder()
                .email(registerReq.getUsername())
                .phone(registerReq.getPhone())
                .firstName(registerReq.getFirstName())
                .lastName(registerReq.getLastName())
                .password(encoder.encode(registerReq.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);
        return true;
    }
}
