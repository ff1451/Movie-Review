package com.ff1451.movie_review.repository;

import com.ff1451.movie_review.entity.ApiPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiPageRepository extends JpaRepository<ApiPage, Long> {
}
