package com.ff1451.movie_review.dto.movie;

import com.ff1451.movie_review.entity.Genre;
import com.ff1451.movie_review.entity.Movie;

import java.util.List;

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
    String movieTime,
    String director,
    List<String> genres
) {
    public static MovieResponse from(Movie movie) {
        List<String> genreNames = movie.getGenres().stream()
            .map(Genre::getGenreName)
            .toList();

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
            movie.getMovieTime(),
            movie.getDirector().getName(),
            genreNames
        );
    }
}
