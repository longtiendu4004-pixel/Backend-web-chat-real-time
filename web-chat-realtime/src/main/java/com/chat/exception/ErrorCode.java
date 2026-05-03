package com.chat.exception;

public enum ErrorCode {
    UNCATEGORIZED(9999, "Lỗi chưa xác định"),
    USER_EXISTED(1001, "Tên tài khoản đã tồn tại"),
    PASSWORD_WEAK(1002, "Mật khẩu cần lớn hơn 8 kí tự"),
    USERNAME_OR_PASSWORD_WRONG(1003, "Sai tài khoản hoặc mật khẩu!")
    ;
    private final int code;
    private final String message;

    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
