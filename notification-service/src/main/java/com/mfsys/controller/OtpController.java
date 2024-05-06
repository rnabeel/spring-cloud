package com.mfsys.controller;

import com.mfsys.dto.OTPRequestDTO;
import com.mfsys.service.EmailService;
import com.mfsys.service.SMSService;
import com.mfsys.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OtpController {

    final private EmailService emailService;
    final private SMSService smsService;
    private final TemplateService templateService;

    @Async
    @PostMapping("/otp/email")
    public ResponseEntity<Void> sendOTPMail(@RequestBody OTPRequestDTO otpRequestDTO) {
        String emailContent = templateService.createOtpEmailContent(otpRequestDTO.getUserName(), otpRequestDTO.getOtp());
        emailService.sendSimpleMessage(otpRequestDTO.getEmail(), otpRequestDTO.getSubject(), emailContent);
        return ResponseEntity.accepted().build();
    }


    @PostMapping("/sendOtpSms")
    public ResponseEntity<HttpStatus> sendOTPSMS(@RequestBody Map request) {
        smsService.sendSMS(" ", " ");
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
