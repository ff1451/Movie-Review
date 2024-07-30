package com.ff1451.movie_review.controller;


import com.ff1451.movie_review.dto.director.DirectorRequest;
import com.ff1451.movie_review.dto.director.DirectorResponse;
import com.ff1451.movie_review.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/director")
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponse> getDirectorById(@PathVariable Long id) {
        DirectorResponse directorResponse = directorService.getDirectorById(id);
        return ResponseEntity.ok(directorResponse);
    }

    @GetMapping()
    public ResponseEntity<List<DirectorResponse>> getAllDirectors() {
        List<DirectorResponse> directorResponse = directorService.getAllDirectors();
        return ResponseEntity.ok(directorResponse);
    }

    @PostMapping
    public ResponseEntity<DirectorResponse> addDirector(@RequestBody DirectorRequest request) {
        DirectorResponse response = directorService.createDirector(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorResponse> updateDirector(@PathVariable Long id, @RequestBody DirectorRequest request) {
        DirectorResponse response = directorService.updateDirector(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DirectorResponse> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }

}
