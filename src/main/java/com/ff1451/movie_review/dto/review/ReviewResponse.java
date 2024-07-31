package com.ff1451.movie_review.dto.review;

import com.ff1451.movie_review.entity.Review;

public record ReviewResponse(
    Long id,
    Long movieId,
    Long userId,
    Float rating,
    String reviewText
)  {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
            review.getId(),
            review.getMovie().getId(),
            review.getUser().getId(),
            review.getRating(),
            review.getReviewText()
        );
    }
}
