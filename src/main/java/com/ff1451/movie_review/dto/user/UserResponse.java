package com.ff1451.movie_review.dto.user;

import com.ff1451.movie_review.entity.User;

public record UserResponse(
    Long id,
    String name,
    String email
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
