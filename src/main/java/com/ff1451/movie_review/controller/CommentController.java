package com.ff1451.movie_review.controller;


import com.ff1451.movie_review.dto.comment.CommentRequest;
import com.ff1451.movie_review.dto.comment.CommentResponse;
import com.ff1451.movie_review.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByReviewId(@PathVariable Long reviewId) {
        List<CommentResponse> responses = commentService.getCommentByReviewId(reviewId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUserId(@PathVariable Long userId) {
        List<CommentResponse> responses = commentService.getCommentByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/parent/{parentCommentId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByParentCommentId(@PathVariable Long parentCommentId) {
        List<CommentResponse> responses = commentService.getChildComment(parentCommentId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest request) {
        CommentResponse response = commentService.createComment(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestParam Long userId, @RequestBody CommentRequest request) {
        CommentResponse response = commentService.updateComment(id, userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @RequestParam Long userId) {
        commentService.deleteComment(id, userId);
        return ResponseEntity.noContent().build();
    }




}
