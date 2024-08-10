package com.ff1451.movie_review.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
    @NotBlank
    String name,
    @NotBlank
    String email,
    @NotBlank
    String password
) {
}
