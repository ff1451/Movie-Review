package com.ff1451.movie_review.service;

import com.ff1451.movie_review.dto.genre.GenreRequest;
import com.ff1451.movie_review.dto.genre.GenreResponse;
import com.ff1451.movie_review.entity.Genre;
import com.ff1451.movie_review.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreService {
    private final GenreRepository genreRepository;

    public List<GenreResponse> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
            .map(GenreResponse::from)
            .toList();
    }

    @Transactional
    public GenreResponse createGenre (GenreRequest request) {
        Genre genre = new Genre(
            request.genreName()
        );
        Genre savedGenre = genreRepository.save(genre);
        return GenreResponse.from(savedGenre);
    }

    @Transactional
    public GenreResponse updateGenre (Long id, GenreRequest request) {
        Genre genre = genreRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Genre not found"));
        genre.setGenreName(request.genreName());
        Genre updatedGenre = genreRepository.save(genre);
        return GenreResponse.from(updatedGenre);
    }

    @Transactional
    public void deleteGenre (Long id) {
        genreRepository.deleteById(id);
    }
}
