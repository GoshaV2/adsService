package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.request.AdRequest;
import ru.skypro.homework.dto.response.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@SecurityRequirement(name = "basic")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {
    @GetMapping
    public ResponseEntity<AdsListResponse> getAds() {
        AdsListResponse adsListResponse = new AdsListResponse();
        adsListResponse.setCount(1);
        adsListResponse.setAdResponseList(List.of(AdResponse.builder()
                .id(1)
                .price(1000)
                .title("test")
                .userId(1)
                .build()
        ));
        return ResponseEntity.ok(adsListResponse);
    }

    @PostMapping
    public ResponseEntity<AdResponse> addAd(@RequestBody AdRequest adRequest) {
        return ResponseEntity.ok(AdResponse.builder()
                .id(1)
                .price(adRequest.getPrice())
                .title(adRequest.getTitle())
                .userId(adRequest.getUserId())
                .build());
    }

    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<CommentListResponse> getComments(@PathVariable(name = "ad_pk") long adId) {
        CommentListResponse commentListResponse = new CommentListResponse();
        commentListResponse.setCount(1);
        commentListResponse.setCommentResponseList(List.of(CommentResponse.builder()
                .id(1)
                .userId(1)
                .createdDate(LocalDateTime.now())
                .text("text")
                .build()));
        return ResponseEntity.ok(commentListResponse);
    }

    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<CommentResponse> addComments(@PathVariable(name = "ad_pk") long adId) {
        return ResponseEntity.ok(CommentResponse.builder()
                .id(1)
                .userId(1)
                .createdDate(LocalDateTime.now())
                .text("text")
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsResponse> getFullAd(@PathVariable(name = "id") long adId) {
        return ResponseEntity.ok(FullAdsResponse.builder()
                .id(adId)
                .price(1000)
                .title("test")
                .authorLastName("last")
                .authorFirstName("first")
                .phone("+79173499547")
                .email("email@mail.ru")
                .description("description")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable(name = "id") long adId) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdResponse> updateAds(@PathVariable(name = "id") long adId, @RequestBody AdRequest adRequest) {
        return ResponseEntity.ok(AdResponse.builder()
                .id(adId)
                .price(adRequest.getPrice())
                .title(adRequest.getTitle())
                .userId(adRequest.getUserId())
                .build());
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable(name = "ad_pk") long adId,
                                                      @PathVariable(name = "id") long commentId) {
        return ResponseEntity.ok(CommentResponse.builder()
                .id(commentId)
                .userId(1)
                .createdDate(LocalDateTime.now())
                .text("text")
                .build());
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "ad_pk") long adId,
                                              @PathVariable(name = "id") long commentId) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable(name = "ad_pk") long adId,
                                                         @PathVariable(name = "id") long commentId) {
        return ResponseEntity.ok(CommentResponse.builder()
                .id(commentId)
                .userId(1)
                .createdDate(LocalDateTime.now())
                .text("text")
                .build());
    }

    @GetMapping("/me")
    public ResponseEntity<AdsListResponse> getUserAds() {
        AdsListResponse adsListResponse = new AdsListResponse();
        adsListResponse.setCount(1);
        adsListResponse.setAdResponseList(List.of(AdResponse.builder()
                .id(1)
                .price(1000)
                .title("test")
                .userId(1)
                .build()
        ));
        return ResponseEntity.ok(adsListResponse);
    }
}

