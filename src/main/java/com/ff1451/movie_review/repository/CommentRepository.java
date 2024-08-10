package com.ff1451.movie_review.repository;

import com.ff1451.movie_review.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewIdAndParentCommentIdIsNull(Long reviewId);
    List<Comment> findByUserIdAndParentCommentIdIsNull(Long userId);
    List<Comment> findByParentCommentId(Long parentCommentId);
}
