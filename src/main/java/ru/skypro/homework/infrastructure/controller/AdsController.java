package ru.skypro.homework.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.service.AdService;
import ru.skypro.homework.core.service.CommentService;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.request.CommentRequest;
import ru.skypro.homework.infrastructure.dto.response.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@SecurityRequirement(name = "basic")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Объявления")
@Validated
public class AdsController {
    private final AdService adService;
    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "Получить все объявления")
    public ResponseEntity<AdListResponse> getAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @GetMapping("/find")
    @Operation(summary = "Найти объявления по названию и описанию")
    public ResponseEntity<AdListResponsePage> getAdsByKeyWord(@RequestParam("keyWord") String keyWord,
                                                              @Positive @RequestParam(name = "page",
                                                                      required = false,
                                                                      defaultValue = "0"
                                                              ) int page,
                                                              @Min(1) @RequestParam(name = "countPerPage",
                                                                      required = false,
                                                                      defaultValue = "50"
                                                              ) int countPerPage) {
        return ResponseEntity.ok(adService.findAds(keyWord, page, countPerPage));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавить новое объявление")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AdResponse> addAd(@Valid @RequestPart("properties") AdRequest adRequest,
                                            @RequestPart("image") MultipartFile multipartFile) {
        return ResponseEntity.ok(adService.addAd(adRequest, multipartFile));
    }

    @GetMapping("/{ad_pk}/comments")
    @Operation(summary = "Получить комментарии к объявлению")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CommentListResponse> getComments(@PathVariable(name = "ad_pk") long adId) {
        return ResponseEntity.ok(commentService.getCommentOfAd(adId));
    }

    @PostMapping("/{ad_pk}/comments")
    @Operation(summary = "Добавить комментарий к объявлению")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CommentResponse> addComments(@PathVariable(name = "ad_pk") long adId,
                                                       @Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.addComment(adId, commentRequest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить объявление с полным описанием")
    @PreAuthorize("permitAll()")
    public ResponseEntity<FullAdResponse> getFullAd(@PathVariable(name = "id") long adId) {
        return ResponseEntity.ok(adService.getFullAd(adId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить объявление")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> removeAd(@PathVariable(name = "id") long adId) {
        adService.removeAd(adId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновить объявление")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AdResponse> updateAds(@PathVariable(name = "id") long adId, @Valid @RequestBody AdRequest adRequest) {
        return ResponseEntity.ok(adService.updateAds(adId, adRequest));
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    @Operation(summary = "Получить комментарий к объявлению")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CommentResponse> getComment(@PathVariable(name = "ad_pk") long adId,
                                                      @PathVariable(name = "id") long commentId) {
        return ResponseEntity.ok(commentService.getCommentResponse(adId, commentId));
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    @Operation(summary = "Удалить комментарий")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "ad_pk") long adId,
                                              @PathVariable(name = "id") long commentId) {
        commentService.removeComment(adId, commentId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ad_pk}/comments/{id}")
    @Operation(summary = "Обновить комментарий")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable(name = "ad_pk") long adId,
                                                         @PathVariable(name = "id") long commentId,
                                                         @Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, commentRequest));
    }

    @GetMapping("/me")
    @Operation(summary = "Получить все объявления пользователя")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AdListResponse> getUserAds(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(adService.getUserAds(user.getId()));
    }

    @GetMapping("/image/{adId}")
    public ResponseEntity<InputStreamResource> getAdImage(@PathVariable("adId") long adId) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(adService.getAdImage(adId)));
    }
}

