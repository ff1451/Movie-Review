package com.ff1451.movie_review.controller;

import com.ff1451.movie_review.dto.user.ChangePasswordRequest;
import com.ff1451.movie_review.dto.user.UserResponse;
import com.ff1451.movie_review.dto.user.UserUpdateRequest;
import com.ff1451.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<UserResponse> updateUser(
        @PathVariable Long id,
        @RequestBody UserUpdateRequest request) {
        UserResponse response = userService.update(id,request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<UserResponse> changePassword(
        @PathVariable Long id,
        @RequestBody ChangePasswordRequest request) {
        UserResponse response = userService.changePassword(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
