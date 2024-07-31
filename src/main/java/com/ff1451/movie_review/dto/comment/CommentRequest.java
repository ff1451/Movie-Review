package com.ff1451.movie_review.dto.comment;

import java.util.Optional;

public record CommentRequest(
    String commentText,
    Long reviewId,
    Long userId,
    Optional<Long> parentCommentId
) {
}
