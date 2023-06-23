package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.request.PasswordRequest;
import ru.skypro.homework.dto.request.UserRequest;
import ru.skypro.homework.dto.response.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SecurityRequirement(name = "basic")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse userResponse = UserResponse.builder().build();
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody PasswordRequest passwordRequest) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateUserImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/users/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().build();
    }
}
