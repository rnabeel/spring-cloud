package com.mfsys.comm.commonservices;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpService {
    private final SecureRandom secureRandom = new SecureRandom();
    private final int OTP_LENGTH = 6;

    public String generateOtp() {
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(secureRandom.nextInt(10));
        }

        return otp.toString();
    }
}
