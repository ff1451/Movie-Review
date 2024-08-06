package com.ff1451.movie_review.repository;

import com.ff1451.movie_review.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<List<Movie>> findByTitle(String title);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.genreName = :genre")
    Optional<List<Movie>> findByGenre(String genre);

    @Query("SELECT m FROM Movie m JOIN m.director d where d.name = : director")
    Optional<List<Movie>> findByDirector(String director);

    @Query("SELECT m FROM Movie m WHERE m.cast LIKE %:castName")
    Optional<List<Movie>> findByCast(String castName);

    @Query("SELECT m FROM Movie m WHERE m.rating BETWEEN : minRating AND : maxRating")
    Optional<List<Movie>> findByRatingRange(@Param("minRating") Float minRating, @Param("maxRating") Float maxRating);

    Optional<List<Movie>> findByRating(Float rating);

    Optional<Movie> findByMovieCode(String movieCd);
}
