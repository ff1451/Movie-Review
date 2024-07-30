package com.ff1451.movie_review.dto.director;

import com.ff1451.movie_review.entity.Director;

public record DirectorResponse(
    Long id,
    String name
) {
    public static DirectorResponse from(Director director) {
        return new DirectorResponse(
            director.getId(),
            director.getName()
        );
    }
}
