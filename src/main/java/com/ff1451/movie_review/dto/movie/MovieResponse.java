package com.ff1451.movie_review.dto.movie;

import com.ff1451.movie_review.entity.Movie;

public record MovieResponse(
    Long id,
    String title,
    String synopsis,
    int releaseYear,
    String viewingAge,
    String language,
    String cast,
    String country,
    Float rating,
    String movieTime
) {
    public static MovieResponse from(Movie movie) {
        return new MovieResponse(
            movie.getId(),
            movie.getTitle(),
            movie.getSynopsis(),
            movie.getReleaseYear(),
            movie.getViewingAge(),
            movie.getLanguage(),
            movie.getCast(),
            movie.getCountry(),
            movie.getRating(),
            movie.getMovieTime()
        );
    }
}
