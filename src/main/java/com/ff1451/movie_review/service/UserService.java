package com.ff1451.movie_review.service;

import com.ff1451.movie_review.dto.user.*;
import com.ff1451.movie_review.entity.User;
import com.ff1451.movie_review.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return UserResponse.from(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map(UserResponse::from)
            .toList();
    }
    @Transactional
    public UserResponse create(UserCreateRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new RuntimeException("이메일이 이미 존재합니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.name(),request.email(),encodedPassword);
        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(()-> new RuntimeException("유저를 찾을 수 없습니다"));

        user.update(request.name(), request.email());
        userRepository.save(user);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse changePassword(Long id, ChangePasswordRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(()-> new RuntimeException("유저를 찾을 수 없습니다"));

        if(!passwordEncoder.matches(request.currentPassword(), user.getPassword())){
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }
        user.changePassword(request.currentPassword());
        userRepository.save(user);
        return UserResponse.from(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void login (LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginRequest.email());

        if(user != null && passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            UserResponse userResponse = UserResponse.from(user);
            HttpSession session = request.getSession();
            session.setAttribute("user", userResponse);
            session.setMaxInactiveInterval(1800);

            Cookie cookie = new Cookie("JSESSIOND", session.getId());
            cookie.setMaxAge(1800);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
