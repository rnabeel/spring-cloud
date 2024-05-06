package com.mfsys.service;

import com.mfsys.dto.AuthenticationRequest;
import com.mfsys.dto.AuthenticationResponse;
import com.mfsys.dto.RegisterRequest;
import com.mfsys.dto.VerifyLoginOtpRequest;
import com.mfsys.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    void register(RegisterRequest request);

    void authenticateAndSendLoginOTP(AuthenticationRequest request);

    AuthenticationResponse authenticate(VerifyLoginOtpRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    User findUserByEmail(String porOrgacode, String email);

    void authenticateUser(String porOrgacode, String email, String password);
}
