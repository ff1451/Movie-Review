package com.ff1451.movie_review.dto.genre;

import com.ff1451.movie_review.entity.Genre;

public record GenreResponse(
    Long id,
    String genreName
) {
    public static GenreResponse from(Genre genre) {
        return new GenreResponse(
            genre.getId(),
            genre.getGenreName()
        );
    }
}
