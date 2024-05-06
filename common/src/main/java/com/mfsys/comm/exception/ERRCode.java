package com.mfsys.comm.exception;

public enum ERRCode implements ErrorMessage {

    USER_ALREADY_REGISTERED("ERR_USER_0001", "User Already Registered"),
    OLD_PASSWORD("ERR_USER_0002", "Old Password not allowed"),
    INVALID_OTP("ERR_USER_0003", "Invalid OTP"),
    USER_NOT_FOUND("ERR_USER_0004", "User not found"),
    INCORRECT_PASSWORD("ERR_USER_0005", "Invalid Password"),
    SAME_PASSWORD("ERR_USER_0006", "same password cannot be set as new password"),
    APPLICATION_PENDING("ERR_USER_0007", "Application is pending"),
    // pin errors
    OLD_PIN_INCORRECT("ERR_USER_0008", "Incorrect transaction PIN. Please try again."),
    ACCOUNT_NOT_FOUND("ERR_USER_0009", "Account Not Found"),
    CR_DR_SAME_ACCOUNT("ERR_USER_0010", "Same cr dr account not allowed")
    ;
    private String code;
    private String description;

    private ERRCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
