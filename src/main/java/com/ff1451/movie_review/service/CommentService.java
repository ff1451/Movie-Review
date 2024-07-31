package com.ff1451.movie_review.service;


import com.ff1451.movie_review.dto.comment.CommentRequest;
import com.ff1451.movie_review.dto.comment.CommentResponse;
import com.ff1451.movie_review.entity.Comment;
import com.ff1451.movie_review.entity.Review;
import com.ff1451.movie_review.entity.User;
import com.ff1451.movie_review.repository.CommentRepository;
import com.ff1451.movie_review.repository.ReviewRepository;
import com.ff1451.movie_review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> getCommentByReviewId(Long reviewId) {
        List<Comment> comments = commentRepository.findByReviewId(reviewId);
        return comments.stream()
            .map(CommentResponse::from)
            .toList();
    }

    public List<CommentResponse> getCommentByUserId(Long userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream()
            .map(CommentResponse::from)
            .toList();
    }

    public List<CommentResponse> getChildComment(Long parentCommentId) {
        List<Comment> comments = commentRepository.findByParentCommentId(parentCommentId);
        return comments.stream()
            .map(CommentResponse::from)
            .toList();
    }

    @Transactional
    public CommentResponse createComment(CommentRequest request) {
        Review review = reviewRepository.findById(request.reviewId())
            .orElseThrow(() -> new RuntimeException("review not found"));
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new RuntimeException("user not found"));

        Comment comment = new Comment(
            review,
            user,
            request.commentText()
        );

        if (request.parentCommentId().isPresent()) {
            Comment parentComment = commentRepository.findById(request.parentCommentId().get()).orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parentComment);
            comment.setDepth(parentComment.getDepth() + 1);
        }

        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.from(savedComment);
    }

    @Transactional
    public CommentResponse updateComment(Long id, CommentRequest request) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("comment not found"));
        comment.setCommentText(request.commentText());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment updatedComment = commentRepository.save(comment);
        return CommentResponse.from(updatedComment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
