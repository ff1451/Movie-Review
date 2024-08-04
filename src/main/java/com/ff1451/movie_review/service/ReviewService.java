package com.ff1451.movie_review.service;

import com.ff1451.movie_review.dto.review.ReviewRequest;
import com.ff1451.movie_review.dto.review.ReviewResponse;
import com.ff1451.movie_review.dto.review.ReviewUpdateRequest;
import com.ff1451.movie_review.entity.Movie;
import com.ff1451.movie_review.entity.Review;
import com.ff1451.movie_review.entity.User;
import com.ff1451.movie_review.exception.CustomException;
import com.ff1451.movie_review.exception.ErrorCode;
import com.ff1451.movie_review.repository.MovieRepository;
import com.ff1451.movie_review.repository.ReviewRepository;
import com.ff1451.movie_review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public List<ReviewResponse> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
            .map(ReviewResponse::from)
            .toList();
    }

    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(()-> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        return ReviewResponse.from(review);
    }

    public List<ReviewResponse> getReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream()
            .map(ReviewResponse::from)
            .toList();
    }

    public List<ReviewResponse> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
            .map(ReviewResponse::from)
            .toList();
    }

    @Transactional
    public ReviewResponse createReview(ReviewRequest request) {
        Movie movie = movieRepository.findById(request.movieId())
            .orElseThrow(()-> new CustomException(ErrorCode.MOVIE_NOT_FOUND));

        User user = userRepository.findById(request.userId())
            .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        reviewRepository.findByMovieIdAndUserId(request.movieId(),request.userId())
            .ifPresent(review -> {
                throw new CustomException(ErrorCode.REVIEW_EXIST);
            });

        Review review = new Review(
            movie,
            user,
            request.rating(),
            request.reviewText());

        updateMovieRating(movie.getId());

        Review savedReview = reviewRepository.save(review);
        return ReviewResponse.from(savedReview);
    }

    @Transactional
    public ReviewResponse updateReview(Long id, Long userId, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        request.rating().ifPresent(review::setRating);
        request.reviewText().ifPresent(review::setReviewText);

        updateMovieRating(review.getMovie().getId());

        Review savedReview = reviewRepository.save(review);
        return ReviewResponse.from(savedReview);
    }

    @Transactional
    public void deleteReview(Long id, Long userId) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        reviewRepository.deleteById(id);
        updateMovieRating(review.getMovie().getId());
    }


    private void updateMovieRating(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(()->new RuntimeException("영화를 찾을 수 없습니다."));

        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        double averageRating = reviews.stream()
            .mapToDouble(Review::getRating)
            .average()
            .orElse(0.0);
        movie.setRating((float) averageRating);
        movieRepository.save(movie);
    }


}
