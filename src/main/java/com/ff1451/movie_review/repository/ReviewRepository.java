package com.ff1451.movie_review.repository;

import com.ff1451.movie_review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovieId(Long movieId);
    List<Review> findByUserId(Long userId);
}
