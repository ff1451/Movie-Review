package com.ff1451.movie_review.controller;

import com.ff1451.movie_review.dto.review.ReviewRequest;
import com.ff1451.movie_review.dto.review.ReviewResponse;
import com.ff1451.movie_review.dto.review.ReviewUpdateRequest;
import com.ff1451.movie_review.dto.user.UserResponse;
import com.ff1451.movie_review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<ReviewResponse> addReview(HttpServletRequest httpRequest, @Valid @RequestBody ReviewRequest request) {
        UserResponse userResponse = (UserResponse) httpRequest.getSession().getAttribute("user");
        Long userId = userResponse.id();
        ReviewResponse response = reviewService.createReview(request, userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, HttpServletRequest httpRequest, @Valid @RequestBody ReviewUpdateRequest request) {
        UserResponse userResponse = (UserResponse) httpRequest.getSession().getAttribute("user");
        Long userId = userResponse.id();
        ReviewResponse response = reviewService.updateReview(id, userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, HttpServletRequest httpRequest) {
        UserResponse userResponse = (UserResponse) httpRequest.getSession().getAttribute("user");
        Long userId = userResponse.id();
        reviewService.deleteReview(id, userId);
        return ResponseEntity.noContent().build();
    }
}
