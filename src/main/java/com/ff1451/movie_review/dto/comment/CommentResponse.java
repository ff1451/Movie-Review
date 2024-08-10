package com.ff1451.movie_review.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ff1451.movie_review.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
    Long id,
    String commentText,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt,
    Long reviewId,
    Long userId,
    List<CommentResponse> childComments
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
            comment.getId(),
            comment.getCommentText(),
            comment.getCreatedAt(),
            comment.getUpdatedAt(),
            comment.getReview().getId(),
            comment.getUser().getId(),
            comment.getChildComments().stream()
                .map(CommentResponse::from)
                .toList()
        );
    }
}
