package com.mfsys.uco.service;

import com.mfsys.comm.commonservices.OtpService;
import com.mfsys.comm.constant.EurekaRegisteredMicroServicesURI;
import com.mfsys.comm.exception.InvalidOTPException;
import com.mfsys.uco.dto.OTPRequest;
import com.mfsys.uco.model.Pin;
import com.mfsys.uco.repository.PinRepository;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Component
public class NotificationService {
    @LoadBalanced
    private final WebClient webClient;
    private final OtpService otpService;
    private final PinRepository pinRepository;

    public NotificationService(WebClient.Builder webClientBuilder, OtpService otpService, PinRepository pinRepository) {
        this.webClient = webClientBuilder.baseUrl(EurekaRegisteredMicroServicesURI.NOTIFICATION_SERVICE_LB).build();
        this.otpService = otpService;
        this.pinRepository = pinRepository;
    }

    public Long sendOtp(OTPRequest otpRequest) {
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
        webClient.post().uri("/notification/otp/email").bodyValue(Map.of("email", otpRequest.getEmail(), "subject", otpRequest.getSubject(), "otp", otp, "userName", otpRequest.getUsername())).retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse
                        -> Mono.error(new RuntimeException("Response has error status."))).bodyToMono(String.class).block();
      return pin.getPinserial();
    }

    public void verifyOtp(String porOrgacode, String email, String obpPincode,String pinType) {
        Optional<Pin> pin = pinRepository.findLatestActiveOtpByUserName(email,pinType);
        if (pin.isPresent() && pin.get().getPincode().equals(obpPincode) && pin.get().getPorOrgacode().equals(porOrgacode)) {
            pin.get().setPinstatus("VERIFIED");
            pinRepository.save(pin.get());
            return;
        }
        throw new InvalidOTPException(porOrgacode);
    }
    public void verifyOtpViaOtpId(String id, String pinType,String obpPincode){
        Pin pin = pinRepository.findsss(id,pinType,obpPincode)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found for ID: " + id));
        pin.setPinstatus("VERIFIED");
        pinRepository.save(pin);
    }
}
