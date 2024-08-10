package com.ff1451.movie_review.dto.movie;

import java.util.List;

public record MovieRequest(
    String title,
    String synopsis,
    String releaseYear,
    String viewingAge,
    String language,
    String cast,
    String country,
    String movieTime,
    String directorName,
    List<String> genreName
) {
}
