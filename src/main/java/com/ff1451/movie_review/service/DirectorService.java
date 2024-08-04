package com.ff1451.movie_review.service;


import com.ff1451.movie_review.dto.director.DirectorRequest;
import com.ff1451.movie_review.dto.director.DirectorResponse;
import com.ff1451.movie_review.entity.Director;
import com.ff1451.movie_review.exception.CustomException;
import com.ff1451.movie_review.exception.ErrorCode;
import com.ff1451.movie_review.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectorService {
    private final DirectorRepository directorRepository;

    @Transactional
    public DirectorResponse createDirector(DirectorRequest request) {
        Director director = new Director();
        director.setName(request.name());

        Director savedDirector = directorRepository.save(director);
        return DirectorResponse.from(savedDirector);
    }

    public List<DirectorResponse> getAllDirectors() {
        List<Director> directors = directorRepository.findAll();
        return directors.stream()
            .map(DirectorResponse::from)
            .toList();
    }

    public DirectorResponse getDirectorById(Long id) {
        Director director = directorRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.DIRECTOR_NOT_FOUND));
        return DirectorResponse.from(director);
    }

    @Transactional
    public DirectorResponse updateDirector(Long id, DirectorRequest request) {
        Director director = directorRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.DIRECTOR_NOT_FOUND));
        director.setName(request.name());

        Director updatedDirector = directorRepository.save(director);
        return DirectorResponse.from(updatedDirector);
    }

    @Transactional
    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }
}
