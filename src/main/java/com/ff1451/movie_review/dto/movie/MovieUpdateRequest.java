package com.ff1451.movie_review.dto.movie;

import java.util.List;
import java.util.Optional;

public record MovieUpdateRequest(
    Optional<String> title,
    Optional<String> synopsis,
    Optional<String> releaseYear,
    Optional<String> viewingAge,
    Optional<String> language,
    Optional<String> cast,
    Optional<String> country,
    Optional<String> movieTime,
    Optional<String> directorName,
    Optional<List<String>> genreName
) {
}
