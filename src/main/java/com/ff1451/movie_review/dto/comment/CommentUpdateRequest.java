package com.ff1451.movie_review.dto.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(
    @NotBlank
    String commentText
) {
}
