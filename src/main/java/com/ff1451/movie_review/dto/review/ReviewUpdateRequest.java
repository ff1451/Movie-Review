package com.ff1451.movie_review.dto.review;

import com.ff1451.movie_review.entity.Review;

import java.util.Optional;

public record ReviewUpdateRequest(
    Optional<Float> rating,
    Optional<String> reviewText
) {
}
