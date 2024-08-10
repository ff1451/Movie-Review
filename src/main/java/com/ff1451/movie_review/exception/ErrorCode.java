package com.ff1451.movie_review.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404,"NOT FOUND", "해당 사용자를 찾을 수 없습니다."),
    USER_EMAIL_EXISTENCE(409,"CONFLICT", "해당 이메일을 사용한 사용자가 이미 존재합니다."),
    INVALID_USER(400, "BAD REQUEST", "존재하지 않는 사용자를 참조하고 있습니다."),
    USER_HAS_REVIEW(400, "BAD REQUEST", "사용자가 작성한 리뷰가 존재합니다."),
    UNAUTHORIZED_ACTION(401, "UNAUTHORIZED", "해당 작업에 대한 권한이 없습니다." ),
    WRONG_PASSWORD(400, "BAD REQUEST", "비밀번호가 일치하지 않습니다."),
    WRONG_PASSWORD_EMAIL(400, "BAD REQUEST", "이메일 혹은 비밀번호가 일치하지 않습니다."),

    MOVIE_NOT_FOUND(404,"NOT FOUND", "해당 영화를 찾을 수 없습니다."),
    //MOVIE_HAS_REVIEW(400, "BAD REQUEST", "영화에 작성된 리뷰가 존재합니다."),
    INVALID_MOVIE(400, "BAD REQUEST", "존재하지 않는 영화를 참조하고 있습니다."),

    DIRECTOR_NOT_FOUND(404,"NOT FOUND", "해당 감독을 찾을 수 없습니다."),
    DIRECTOR_HAS_MOVIE(400, "BAD REQUEST", "해당 감독이 연관된 영화가 있습니다."),

    GENRE_NOT_FOUND(404,"NOT FOUND", "해당 장르를 찾을 수 없습니다."),
    GENRE_HAS_MOVIE(400, "BAD REQUEST", "해당 장르에 속한 영화가 있습니다."),

    REVIEW_NOT_FOUND(404, "NOT FOUND", "해당 리뷰를 찾을 수 없습니다."),
    REVIEW_EXIST(409,"CONFLICT","사용자가 해당 영화에 대해 작성한 리뷰가 존재합니다."),
    INVALID_RATING(400, "BAD REQUEST", "평점은 0 이상 5 이하이어야 합니다."),

    COMMENT_NOT_FOUND(404, "NOT FOUND", "해당 댓글를 찾을 수 없습니다."),

    NULL_VALUE(400,"BAD REQUEST", "요청 중 null 값이 존재합니다."),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR","서버 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


}
