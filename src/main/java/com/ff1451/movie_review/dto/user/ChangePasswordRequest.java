package com.ff1451.movie_review.dto.user;

public record ChangePasswordRequest (
    String currentPassword,
    String newPassword
){
}
