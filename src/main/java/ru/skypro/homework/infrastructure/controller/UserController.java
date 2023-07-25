package ru.skypro.homework.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.service.UserService;
import ru.skypro.homework.infrastructure.dto.request.PasswordRequest;
import ru.skypro.homework.infrastructure.dto.request.UserRequest;
import ru.skypro.homework.infrastructure.dto.response.UserResponse;

import javax.validation.Valid;

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
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getUserResponse());
    }

    @GetMapping(value = "/image/{userId}")
    public ResponseEntity<InputStreamResource> getUserImage(@PathVariable("userId") long userId) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(userService.getUserImage(userId)));
    }

    @PostMapping("/set_password")
    @Operation(summary = "Обновить пароль")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> setPassword(@Valid @RequestBody PasswordRequest passwordRequest) {
        userService.changePassword(passwordRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me/image")
    @Operation(summary = "Обновить аватар")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile file) {
        userService.updateUserImage(file);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновить пользователя")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserResponse> updateCurrentUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }
}
