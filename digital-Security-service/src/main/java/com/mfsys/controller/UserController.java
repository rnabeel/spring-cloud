package com.mfsys.controller;

import com.mfsys.SecurityURI;
import com.mfsys.dto.*;
import com.mfsys.repository.UserRepository;
import com.mfsys.service.AuthenticationService;
import com.mfsys.service.UserService;
import com.mfsys.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @PatchMapping(SecurityURI.CHANGE_PASSWORD)
    @ResponseStatus(HttpStatus.OK)
    public boolean changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }

    @PostMapping(SecurityURI.SIGNUP_OTP)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sendSignUpOtp(@RequestBody OtpRequest otpRequest) {
        userService.sendOtp(otpRequest);
        return ResponseEntity.ok("OTP sent");
    }

    @PostMapping(SecurityURI.VERIFY_SINGUP_OTP)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> verifySignUpOtp(@RequestBody VerifySignupOtpRequest verifySignupOtpRequest) {
        userService.verifySignupOtp(verifySignupOtpRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(SecurityURI.VERIFY_LOGIN_OTP)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationResponse> verifyloginInOtp(@RequestBody VerifyLoginOtpRequest verifyLoginOtpRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(verifyLoginOtpRequest));
    }

}
