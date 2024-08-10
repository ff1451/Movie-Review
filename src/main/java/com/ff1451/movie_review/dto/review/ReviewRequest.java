package com.ff1451.movie_review.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewRequest(
    Long movieId,
    @Min(0) @Max(5)
    Float rating,
    String reviewText
) {
}
