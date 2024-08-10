package com.ff1451.movie_review.controller;

import com.ff1451.movie_review.dto.movie.*;
import com.ff1451.movie_review.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/api/list/{page}")
    public MovieListApiResponse getMovieList(@PathVariable int page) {
        return movieService.getMovieListFromApi(page);
    }

    @GetMapping("/api/detail/{movieCode}")
    public MovieDetailInfoApiResponse getMovieDetail(@PathVariable String movieCode) {
        return movieService.getMovieDetailFromApi(movieCode);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<MovieResponse> responses = movieService.getAllMovies();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        MovieResponse response = movieService.getMovieById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<MovieResponse>> getMovieByTitle(@PathVariable String title) {
        List<MovieResponse> responses = movieService.getMovieByTitle(title);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieResponse>> getMovieByGenre(@PathVariable String genre) {
        List<MovieResponse> responses = movieService.getMovieByGenre(genre);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/director/{director}")
    public ResponseEntity<List<MovieResponse>> getMovieByDirector(@PathVariable String director) {
        List<MovieResponse> responses = movieService.getMovieByDirector(director);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/castName/{castName}")
    public ResponseEntity<List<MovieResponse>> getMovieByCastName(@PathVariable String castName) {
        List<MovieResponse> responses = movieService.getMovieByCast(castName);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/rating/{min}/{max}")
    public ResponseEntity<List<MovieResponse>> getMovieByRating(@PathVariable Float min, @PathVariable Float max) {
        List<MovieResponse> responses = movieService.getMovieByRatingRange(min,max);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<MovieResponse>> getMovieByRating(@PathVariable Float rating) {
        List<MovieResponse> responses = movieService.getMovieByRating(rating);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/add")
    public ResponseEntity<MovieResponse> addMovie(@Valid  @RequestBody MovieRequest request) {
        MovieResponse response = movieService.insertMovie(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/movie")
    public ResponseEntity<Void> updateMoviesFromApi() {
        movieService.updateAllMovies();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @RequestBody MovieUpdateRequest request) {
        MovieResponse response = movieService.updateMovie(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<MovieResponse> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
