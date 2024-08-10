package com.ff1451.movie_review.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MovieRequest(
    @NotBlank
    String title,
    @NotNull
    String synopsis,
    @NotNull
    String releaseYear,
    @NotNull
    String viewingAge,
    @NotNull
    String language,
    @NotNull
    String cast,
    @NotNull
    String country,
    @NotNull
    String movieTime,
    @NotNull
    String directorName,
    @NotNull
    List<String> genreName
) {
}
