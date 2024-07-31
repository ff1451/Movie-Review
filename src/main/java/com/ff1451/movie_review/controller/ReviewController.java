package com.ff1451.movie_review.controller;

import com.ff1451.movie_review.dto.review.ReviewRequest;
import com.ff1451.movie_review.dto.review.ReviewResponse;
import com.ff1451.movie_review.dto.review.ReviewUpdateRequest;
import com.ff1451.movie_review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> responses = reviewService.getAllReviews();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        ReviewResponse response = reviewService.getReviewById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByMovieId(@PathVariable Long movieId) {
        List<ReviewResponse> responses = reviewService.getReviewsByMovieId(movieId);
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewResponse> responses = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequest request) {
        ReviewResponse response = reviewService.updateReview(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
