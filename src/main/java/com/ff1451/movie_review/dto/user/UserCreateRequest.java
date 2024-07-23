package com.ff1451.movie_review.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
    @NotBlank(message = "이름을 입력해주세요")
    String name,
    @NotBlank(message = "이메일을 입력해주세요")
    String email,
    @NotBlank(message = "비밀번호를 입력해주세요")
    String password

) {
}
