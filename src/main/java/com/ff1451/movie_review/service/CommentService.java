package com.ff1451.movie_review.service;


import com.ff1451.movie_review.dto.comment.CommentRequest;
import com.ff1451.movie_review.dto.comment.CommentResponse;
import com.ff1451.movie_review.dto.comment.CommentUpdateRequest;
import com.ff1451.movie_review.entity.Comment;
import com.ff1451.movie_review.entity.Review;
import com.ff1451.movie_review.entity.User;
import com.ff1451.movie_review.exception.CustomException;
import com.ff1451.movie_review.exception.ErrorCode;
import com.ff1451.movie_review.repository.CommentRepository;
import com.ff1451.movie_review.repository.ReviewRepository;
import com.ff1451.movie_review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> getCommentByReviewId(Long reviewId) {
        List<Comment> comments = commentRepository.findByReviewIdAndParentCommentIdIsNull(reviewId);
        return comments.stream()
            .map(CommentResponse::from)
            .toList();
    }

    public List<CommentResponse> getCommentByUserId(Long userId) {
        List<Comment> comments = commentRepository.findByUserIdAndParentCommentIdIsNull(userId);
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
    public CommentResponse createComment(Long userId, CommentRequest request) {
        Review review = reviewRepository.findById(request.reviewId())
            .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

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
    public CommentResponse updateComment(Long id, Long userId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        comment.setCommentText(request.commentText());
        Comment updatedComment = commentRepository.save(comment);
        return CommentResponse.from(updatedComment);
    }

    @Transactional
    public void deleteComment(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(()-> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        if (comment.getChildComments().isEmpty()){
            commentRepository.delete(comment);
        } else{
            comment.setCommentText("삭제된 댓글입니다.");
            commentRepository.save(comment);
        }
    }

}
