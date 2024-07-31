package com.ff1451.movie_review.dto.review;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReviewRequest(
    Long movieId,
    Long userId,
    Float rating,
    String reviewText
) {
}
