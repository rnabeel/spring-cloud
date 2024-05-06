package com.mfsys.service;

import com.mfsys.dto.ChangePasswordRequest;
import com.mfsys.dto.OtpRequest;
import com.mfsys.dto.VerifyLoginOtpRequest;
import com.mfsys.dto.VerifySignupOtpRequest;
import org.springframework.scheduling.annotation.Async;
import reactor.core.publisher.Mono;

public interface UserService {
    boolean changePassword(ChangePasswordRequest changePasswordRequest);

    @Async
    Mono<String> sendOtp(OtpRequest otpRequest);

    void verifySignupOtp(VerifySignupOtpRequest signupOtpRequest);

    void verifyLoginOtp(VerifyLoginOtpRequest verifyLoginOtpRequest);

    void verifyOtp(String porOrgacode, String email, String obpPincode);
}
