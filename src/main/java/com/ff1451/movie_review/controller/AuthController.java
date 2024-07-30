package com.ff1451.movie_review.controller;

import com.ff1451.movie_review.dto.user.LoginRequest;
import com.ff1451.movie_review.dto.user.UserCreateRequest;
import com.ff1451.movie_review.dto.user.UserResponse;
import com.ff1451.movie_review.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
        @Valid @RequestBody LoginRequest loginRequest,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        userService.login(loginRequest, request, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        HttpServletRequest request,
        HttpServletResponse response) {
        userService.logout(request, response);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreateRequest request){
        UserResponse response = userService.create(request);
        return ResponseEntity.ok(response);
    }
}
