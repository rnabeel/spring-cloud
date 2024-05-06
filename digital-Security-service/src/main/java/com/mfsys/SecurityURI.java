package com.mfsys;

public interface SecurityURI {
    String REGISTER_USER = "/auth/user/register";
    String AUTHENTICATE_USER = "/auth/user/authenticate";
    String REFRESH_TOKEN = "/auth/refresh-token";
    String SIGNUP_OTP = "/auth/signup-otp";
    String VERIFY_SINGUP_OTP = "/auth/verifysignup-otp";
    String VERIFY_LOGIN_OTP = "/auth/verifylogin-otp";
    String CHANGE_PASSWORD = "/user/changePassword";
    String RESET_PASSWORD = "/user/resetPassword";
    String GENERATE_INTERNAL_TOKEN = "/auth/generateInternalToken";
}
