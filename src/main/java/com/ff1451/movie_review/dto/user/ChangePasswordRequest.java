package com.ff1451.movie_review.dto.user;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest (
    @NotBlank
    String currentPassword,
    @NotBlank
    String newPassword
){
}
