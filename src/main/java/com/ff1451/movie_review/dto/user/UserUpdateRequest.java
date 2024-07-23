package com.ff1451.movie_review.dto.user;

public record UserUpdateRequest(
    String name,
    String email,
    String password
) {
}
