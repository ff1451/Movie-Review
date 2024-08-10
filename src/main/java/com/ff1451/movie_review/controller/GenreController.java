package com.ff1451.movie_review.controller;

import com.ff1451.movie_review.dto.genre.GenreRequest;
import com.ff1451.movie_review.dto.genre.GenreResponse;
import com.ff1451.movie_review.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenre() {
        List<GenreResponse> responses = genreService.getAllGenres();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<GenreResponse> addGenre(@Valid  @RequestBody GenreRequest request) {
        GenreResponse response = genreService.createGenre(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponse> updateGenre(@PathVariable Long id, @Valid @RequestBody GenreRequest request) {
        GenreResponse response = genreService.updateGenre(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenreResponse> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

}
