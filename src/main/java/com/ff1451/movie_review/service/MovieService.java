package com.ff1451.movie_review.service;

import com.ff1451.movie_review.dto.movie.MovieRequest;
import com.ff1451.movie_review.dto.movie.MovieResponse;
import com.ff1451.movie_review.dto.movie.MovieUpdateRequest;
import com.ff1451.movie_review.entity.Director;
import com.ff1451.movie_review.entity.Genre;
import com.ff1451.movie_review.entity.Movie;
import com.ff1451.movie_review.repository.DirectorRepository;
import com.ff1451.movie_review.repository.GenreRepository;
import com.ff1451.movie_review.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;

    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(()->new RuntimeException("없음"));
        return MovieResponse.from(movie);
    }
    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByTitle(String title) {
        List<Movie> movies = movieRepository.findByTitle(title)
            .orElseThrow(()-> new RuntimeException("없음"));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByGenre(String genre) {
        List<Movie> movies = movieRepository.findByGenre(genre)
            .orElseThrow(()-> new RuntimeException("없음"));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByDirector(String director) {
        List<Movie> movies = movieRepository.findByDirector(director)
            .orElseThrow(()-> new RuntimeException("없음"));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByCast(String castName) {
        List<Movie> movies = movieRepository.findByCast(castName)
            .orElseThrow(()-> new RuntimeException("없음"));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByRatingRange(Float minRating, Float maxRating) {
        List<Movie> movies = movieRepository.findByRatingRange(minRating, maxRating)
            .orElseThrow(()-> new RuntimeException("없음"));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }

    public List<MovieResponse> getMovieByRating(Float rating) {
        List<Movie> movies = movieRepository.findByRating(rating)
            .orElseThrow(()-> new RuntimeException("없음"));
        return movies.stream()
            .map(MovieResponse::from)
            .toList();
    }


    @Transactional
    public MovieResponse insertMovie(MovieRequest request) {
        Movie movie = new Movie(
            request.title(),
            request.synopsis(),
            request.releaseYear(),
            request.viewingAge(),
            request.language(),
            request.cast(),
            request.country(),
            request.movieTime()
        );

        Director director = directorRepository.findByName(request.directorName())
            .orElseGet(()->{
                Director newDirector = new Director(request.directorName());
                return directorRepository.save(newDirector);
            });
        movie.setDirector(director);

        List<Genre> genres = request.genreName().stream()
            .map(name -> genreRepository.findByGenreName(name)
                .orElseGet(() -> {
                    Genre newGenre = new Genre(name);
                    return genreRepository.save(newGenre);
                }))
            .toList();
        movie.setGenres(genres);

        Movie savedMovie = movieRepository.save(movie);
        return MovieResponse.from(savedMovie);
    }

    @Transactional
    public MovieResponse updateMovie(Long id, MovieUpdateRequest request) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(()->new RuntimeException("영화를 찾을 수 없습니다."));

        request.title().ifPresent(movie::setTitle);
        request.synopsis().ifPresent(movie::setSynopsis);
        request.releaseYear().ifPresent(movie::setReleaseYear);
        request.viewingAge().ifPresent(movie::setViewingAge);
        request.language().ifPresent(movie::setLanguage);
        request.cast().ifPresent(movie::setCast);
        request.country().ifPresent(movie::setCountry);
        request.movieTime().ifPresent(movie::setMovieTime);

        if (request.directorName().isPresent()) {
            Director director = directorRepository.findByName(request.directorName().get())
                .orElseGet(() -> {
                    Director newDirector = new Director(request.directorName().get());
                    return directorRepository.save(newDirector);
                });
            movie.setDirector(director);
        }

        if (request.genreName().isPresent()) {
            List<Genre> genres = request.genreName().get().stream()
                .map(name -> genreRepository.findByGenreName(name)
                    .orElseGet(() -> {
                        Genre newGenre = new Genre();
                        newGenre.setGenreName(name);
                        return genreRepository.save(newGenre);
                    }))
                .toList();
            movie.setGenres(genres);
        }

        Movie savedMovie = movieRepository.save(movie);
        return MovieResponse.from(savedMovie);
    }

    @Transactional
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
