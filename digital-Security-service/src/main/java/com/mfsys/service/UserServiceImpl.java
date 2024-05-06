package com.mfsys.service;

import com.mfsys.comm.commonservices.OtpService;
import com.mfsys.comm.constant.EurekaRegisteredMicroServicesURI;
import com.mfsys.comm.exception.InvalidOTPException;
import com.mfsys.dto.ChangePasswordRequest;
import com.mfsys.dto.OtpRequest;
import com.mfsys.dto.VerifyLoginOtpRequest;
import com.mfsys.dto.VerifySignupOtpRequest;
import com.mfsys.exception.OldPasswordNotAllowedException;
import com.mfsys.exception.PasswordIncorrectException;
import com.mfsys.exception.SamePasswordException;
import com.mfsys.exception.UserNotFoundException;
import com.mfsys.model.Pin;
import com.mfsys.repository.PinRepository;
import com.mfsys.repository.UserRepository;
import com.mfsys.user.PreviousPassword;
import com.mfsys.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final PinRepository pinRepository;
    @LoadBalanced
    private final WebClient webClient;
    @LoadBalanced
    private final WebClient webClientUco;

    @Autowired
    public UserServiceImpl(WebClient.Builder webClientBuilder, UserRepository userRepository, PasswordEncoder passwordEncoder, OtpService otpService
            , PinRepository pinRepository, WebClient.Builder webClientUco) {
        this.webClient = webClientBuilder.baseUrl(EurekaRegisteredMicroServicesURI.NOTIFICATION_SERVICE_LB).build();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.pinRepository = pinRepository;
        this.webClientUco = webClientBuilder.baseUrl(EurekaRegisteredMicroServicesURI.NOTIFICATION_SERVICE_LB).build();;
    }

    @Override
    public boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        var user = userRepository.findByEmail(changePasswordRequest.getEmail()).orElseThrow(
                () -> new UserNotFoundException(null)
        );

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(),user.getPassword())) {
            throw new PasswordIncorrectException(null);
        }

        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(),user.getPassword())) {
            throw new SamePasswordException();
        }
        List<PreviousPassword> previousPasswords = user.getPreviousPasswords();
        boolean matchesOldPassword = previousPasswords.stream()
                .sorted(Comparator.comparing(PreviousPassword::getId).reversed())
                .limit(3).anyMatch(pp -> passwordEncoder
                        .matches(changePasswordRequest.getNewPassword(), pp.getPassword()));
        if (matchesOldPassword) {
            throw new OldPasswordNotAllowedException(user.getPorOrgacode());
        } else {
            PreviousPassword previousPassword = new PreviousPassword();
            previousPassword.setPassword(passwordEncoder.encode(user.getPassword()));
            previousPassword.setUser(user);
            previousPasswords.add(previousPassword);
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public Mono<String> sendOtp(OtpRequest otpRequest) {
        String otp = otpRequest.isOtpRequired() ? otpService.generateOtp() : "123456";
        Pin pin = new Pin();
        final LocalDateTime createDate = LocalDateTime.now();
        final LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);
        pin.setPinCreatedate(createDate);
        pin.setPinExpirydate(expiryDate);
        pin.setChannelCode(otpRequest.getChannelCode());
        pin.setPintype(otpRequest.getPinType());
        pin.setPincode(otp);
        pin.setPorOrgacode(otpRequest.getPorOrgacode());
        pin.setVersion(1);
        pin.setPinlength(6);
        pin.setPinstatus("Unverified");
        pin.setUserName(otpRequest.getEmail());
        pinRepository.save(pin);
        webClient.post().uri("/notification/otp/email").bodyValue(Map.of("email", otpRequest.getEmail(), "subject", "OTP Code", "otp", otp, "userName", otpRequest.getUsername())).retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse
                        -> Mono.error(new RuntimeException("Response has error status."))).bodyToMono(String.class).block();
        System.out.println("otp sent");
       return null;
    }

    @Override
    public void verifySignupOtp(VerifySignupOtpRequest verifySignupOtpRequest) {
        verifyOtp(verifySignupOtpRequest.getPorOrgacode(), verifySignupOtpRequest.getEmail(), verifySignupOtpRequest.getObpPincode());
        Optional<User> user = userRepository.findByEmail(verifySignupOtpRequest.getEmail());
        if(user.isPresent()){
            user.get().setPending(false);
            userRepository.save(user.get());
        }

    }

    @Override
    public void verifyLoginOtp(VerifyLoginOtpRequest verifyLoginOtpRequest) {
        verifyOtp(verifyLoginOtpRequest.getPorOrgacode(), verifyLoginOtpRequest.getEmail(), verifyLoginOtpRequest.getObpPincode());
    }

    @Override
    public void verifyOtp(String porOrgacode, String email, String obpPincode) {
        Optional<Pin> pin = pinRepository.findLatestActiveOtpByUserName(email);
        if (pin.isPresent() && pin.get().getPincode().equals(obpPincode) && pin.get().getPorOrgacode().equals(porOrgacode)) {
            pin.get().setPinstatus("VERIFIED");
            pinRepository.save(pin.get());
            return;
        }
        throw new InvalidOTPException(porOrgacode);
    }

}