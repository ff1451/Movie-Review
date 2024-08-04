package com.ff1451.movie_review.dto.review;

import java.util.Optional;

public record ReviewUpdateRequest(
    Optional<Float> rating,
    Optional<String> reviewText
) {
}
