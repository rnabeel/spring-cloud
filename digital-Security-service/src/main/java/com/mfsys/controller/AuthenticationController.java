package com.mfsys.controller;

import com.mfsys.SecurityURI;
import com.mfsys.dto.AuthenticationRequest;
import com.mfsys.dto.RegisterRequest;
import com.mfsys.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(SecurityURI.REGISTER_USER)
    public ResponseEntity<HttpStatus> register(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @PostMapping(SecurityURI.AUTHENTICATE_USER)
    public void authenticate(@RequestBody AuthenticationRequest request) {
        authenticationService.authenticateAndSendLoginOTP(request);
    }


    @PostMapping(SecurityURI.REFRESH_TOKEN)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

}
