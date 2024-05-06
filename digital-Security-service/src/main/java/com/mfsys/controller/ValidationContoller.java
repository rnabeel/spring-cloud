package com.mfsys.controller;

import com.mfsys.SecurityURI;
import com.mfsys.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidationContoller {

    private final JwtService jwtService;

    @PostMapping(SecurityURI.GENERATE_INTERNAL_TOKEN)
    public String generateInternalToken(@RequestBody String token) {
        return jwtService.generateInternalToken(token);
    }

}
