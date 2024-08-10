package com.ff1451.movie_review.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
    @NotBlank
    Long movieId,
    @NotBlank @Min(0) @Max(5)
    Float rating,
    @NotNull
    String reviewText
) {
}
