package ru.skypro.homework.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.service.UserService;
import ru.skypro.homework.infrastructure.dto.request.PasswordRequest;
import ru.skypro.homework.infrastructure.dto.request.UserRequest;
import ru.skypro.homework.infrastructure.dto.response.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SecurityRequirement(name = "basic")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Пользователь")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Получить текущего пользователя")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getUserResponse());
    }

    @PostMapping("/set_password")
    @Operation(summary = "Обновить пароль")
    public ResponseEntity<Void> setPassword(@RequestBody PasswordRequest passwordRequest) {
        userService.changePassword(passwordRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me/image")
    @Operation(summary = "Обновить аватар")
    public ResponseEntity<Void> updateUserImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновить пользователя")
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }
}
