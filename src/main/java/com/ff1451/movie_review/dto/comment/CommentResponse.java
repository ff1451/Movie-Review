package com.ff1451.movie_review.dto.comment;

import com.ff1451.movie_review.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
    Long id,
    String commentText,
    int depth,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long reviewId,
    Long userId,
    Long parentCommentId,
    List<CommentResponse> childComments
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
            comment.getId(),
            comment.getCommentText(),
            comment.getDepth(),
            comment.getCreatedAt(),
            comment.getUpdatedAt(),
            comment.getReview().getId(),
            comment.getUser().getId(),
            comment.getParentComment() != null ? comment.getParentComment().getId() : null,
            comment.getChildComments().stream()
                .map(CommentResponse::from)
                .toList()
        );
    }
}
