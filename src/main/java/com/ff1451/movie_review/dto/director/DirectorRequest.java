package com.ff1451.movie_review.dto.director;

import jakarta.validation.constraints.NotBlank;

public record DirectorRequest(
    @NotBlank
    String name
) {
}
