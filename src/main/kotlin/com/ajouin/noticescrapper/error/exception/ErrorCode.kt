package com.ajouin.noticescrapper.error.exception

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {
    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력이에요."),
    METHOD_NOT_ALLOWED(405, "C002", "허용되지 않은 메소드에요."),
    ENTITY_NOT_FOUND(400, "C003", "대상을 찾을 수 없어요."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 에러에요. 잠시 후, 다시 시도해주세요."),
    INVALID_TYPE_VALUE(400, "C005", "잘못된 타입이에요."),
    HANDLE_ACCESS_DENIED(403, "C006", "권한이 없어요."),
    NOT_VERIFIED(403, "C007", "인증되지 않은 사용자에요."),
    INVALID_TOKEN(401, "C008", "유효하지 않은 토큰이에요."),
    TOKEN_EXPIRED(401, "C009", "만료된 토큰이에요."),

    // Member, Auth
    EMAIL_DUPLICATION(400, "M001", "이미 사용중인 이메일이에요."),
    MEMBER_NOT_FOUND(400, "M002", "존재하지 않는 회원이에요."),
    LOGIN_INPUT_INVALID(400, "M003", "이메일 또는 비밀번호가 잘못되었어요."),
    NICKNAME_DUPLICATION(400, "M004", "이미 사용중인 닉네임이에요."),
    TIMEOUT(400, "M005", "인증 시간이 초과되었어요."),
    CODE_INVALID(400, "M006", "인증 코드가 잘못되었어요."),
    INAPPROPRIATE_NICKNAME(400, "M007", "부적절한 닉네임이에요."),
    NOT_ALLOWED_NICKNAME_LENGTH(400, "M008", "닉네임은 2자 이상, 10자 이하여야 해요."),
    EMAIL_INVALID(400, "M009", "학교 이메일이 아니에요."),
    EMAIL_NOT_EXIST(400, "M010", "해당 이메일로 가입된 계정이 존재하지 않아요."),
    EMAIL_VERIFICATION_NOT_FOUND(400, "M011", "이메일 인증 정보를 찾을 수 없어요."),

    // Post
    POST_TITLE_LENGTH_INVALID(400, "P001", "게시글 제목은 2글자 이상이여야 해요."),
    POST_CONTENT_LENGTH_INVALID(400, "P002", "게시글 내용은 2글자 이상이여야 해요."),
    POST_TAG_INVALID(400, "P003", "게시글 태그를 지정해주세요."),
    POST_TITLE_VALUE_INVALID(400, "P004", "제목이 부적절해요."),
    POST_CONTENT_VALUE_INVALID(400, "P005", "부적절한 내용이에요."),
    POST_COMMENT_LENGTH_INVALID(400, "P006","댓글 내용은 2글자 이상이여야 해요."),

    // SchoolNotice
    SCHOOL_NOTICE_NOT_FOUND(400, "S001", "존재하지 않는 공지사항이에요."),

    // Wiki
    CATEGORY_NOT_FOUND(400, "W001", "존재하지 않는 카테고리에요."),
    DOCUMENT_NOT_FOUND(400, "W002", "존재하지 않는 문서에요."),
}