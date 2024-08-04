package com.ff1451.movie_review.dto.review;

public record ReviewRequest(
    Long movieId,
    Long userId,
    Float rating,
    String reviewText
) {
}
