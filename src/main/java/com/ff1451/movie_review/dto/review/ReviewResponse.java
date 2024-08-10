package com.ff1451.movie_review.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ff1451.movie_review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
    Long id,
    Long movieId,
    String title,
    Long userId,
    Float rating,
    String reviewText,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt
)  {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
            review.getId(),
            review.getMovie().getId(),
            review.getMovie().getTitle(),
            review.getUser().getId(),
            review.getRating(),
            review.getReviewText(),
            review.getCreatedAt(),
            review.getUpdatedAt()
        );
    }
}
