package com.ff1451.movie_review.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Optional;

public record ReviewUpdateRequest(
    @Min(0) @Max(5)
    Optional<Float> rating,
    Optional<String> reviewText
) {
}
