package com.ff1451.movie_review.dto.genre;

import jakarta.validation.constraints.NotBlank;

public record GenreRequest(
    @NotBlank
    String genreName
) {
}
