package com.ff1451.movie_review.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record CommentRequest(
    @NotNull
    String commentText,
    @NotBlank
    Long reviewId,
    Optional<Long> parentCommentId
) {
}
