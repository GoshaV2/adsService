package ru.skypro.homework.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.service.AdService;
import ru.skypro.homework.core.service.CommentService;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.request.CommentRequest;
import ru.skypro.homework.infrastructure.dto.response.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@SecurityRequirement(name = "basic")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Объявления")
public class AdsController {
    private final AdService adService;
    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "Получить все объявления")
    public ResponseEntity<AdsListResponse> getAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавить новое объявление")
    public ResponseEntity<AdResponse> addAd(@RequestPart("properties") AdRequest adRequest,
                                            @RequestPart("image") MultipartFile multipartFile,
                                            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(adService.addAd(adRequest, multipartFile, user));
    }

    @GetMapping("/{ad_pk}/comments")
    @Operation(summary = "Получить комментарии к объявлению")
    public ResponseEntity<CommentListResponse> getComments(@PathVariable(name = "ad_pk") long adId) {
        return ResponseEntity.ok(commentService.getCommentOfAd(adId));
    }

    @PostMapping("/{ad_pk}/comments")
    @Operation(summary = "Добавить комментарий к объявлению")
    public ResponseEntity<CommentResponse> addComments(@PathVariable(name = "ad_pk") long adId,
                                                       @RequestBody CommentRequest commentRequest,
                                                       @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(commentService.addComment(adId, commentRequest, user));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить объявление с полным описанием")
    public ResponseEntity<FullAdResponse> getFullAd(@PathVariable(name = "id") long adId) {
        return ResponseEntity.ok(adService.getFullAd(adId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить объявление")
    public ResponseEntity<Void> removeAd(@PathVariable(name = "id") long adId) {
        adService.removeAd(adId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновить объявление")
    public ResponseEntity<AdResponse> updateAds(@PathVariable(name = "id") long adId, @RequestBody AdRequest adRequest) {
        return ResponseEntity.ok(adService.updateAds(adId, adRequest));
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    @Operation(summary = "Получить комментарий к объявлению")
    public ResponseEntity<CommentResponse> getComment(@PathVariable(name = "ad_pk") long adId,
                                                      @PathVariable(name = "id") long commentId) {
        return ResponseEntity.ok(commentService.getCommentResponse(adId, commentId));
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    @Operation(summary = "Удалить комментарий")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "ad_pk") long adId,
                                              @PathVariable(name = "id") long commentId) {
        commentService.removeComment(adId, commentId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ad_pk}/comments/{id}")
    @Operation(summary = "Обновить комментарий")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable(name = "ad_pk") long adId,
                                                         @PathVariable(name = "id") long commentId,
                                                         @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, commentRequest));
    }

    @GetMapping("/me")
    @Operation(summary = "Получить все объявления пользователя")
    public ResponseEntity<AdsListResponse> getUserAds(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(adService.getUserAds(user.getId()));
    }
}

