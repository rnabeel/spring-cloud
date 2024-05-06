package com.mfsys.uco.service;

import com.mfsys.uco.dto.ChangeTransactionPinRequest;
import com.mfsys.uco.dto.CreateTransactionPinRequest;
import com.mfsys.uco.dto.OTPRequest;
import com.mfsys.uco.dto.VerifyPinRequest;
import com.mfsys.uco.exception.OldPinIncorrectException;
import com.mfsys.uco.model.CustomerProfile;
import com.mfsys.uco.model.CustomerProfileId;
import com.mfsys.uco.repository.CustomerProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionPinService {

    private final CustomerProfileRepository customerProfileRepository;
    private final NotificationService notificationService;

    public void createTransactionPin(CreateTransactionPinRequest request) {
        CustomerProfile profile = fetchCustomer(request.getPorOrgacode(), request.getCmpCustcode());
        profile.setCmpTranpin(request.getNewTransPincode());
        sendOtp(profile, request.getChannelCode(), request.getPinType(), "Create Transaction Pin Verification OTP", request.isOtpRequired());
        customerProfileRepository.save(profile);
    }


    public boolean verifyOTPAndSavePin(VerifyPinRequest request) {
        notificationService.verifyOtp(request.getPorOrgacode(), request.getEmail(), request.getObpPincode(), request.getPinType());
        CustomerProfile profile = fetchCustomer(request.getPorOrgacode(), request.getCmpCustcode());
        if(Objects.nonNull(profile.getCmpUnverifiedTranpin())) {
            profile.setCmpTranpin(profile.getCmpUnverifiedTranpin());
            profile.setCmpUnverifiedTranpin(null);
        }
        profile.setCmpTranpinVerfied(true);
        customerProfileRepository.save(profile);
        return true;
    }

    public CustomerProfile fetchCustomer(String porOrgacode, String cmpCustcode){
        CustomerProfileId profileId = new CustomerProfileId(porOrgacode,cmpCustcode);
       return customerProfileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Customer profile not found for ID: " + profileId));
    }

    public void updateTransactionPin(ChangeTransactionPinRequest request) {
        CustomerProfile profile = fetchCustomer(request.getPorOrgacode(), request.getCmpCustcode());
        validateOldPin(profile, request.getOldTransPincode());
        profile.setCmpUnverifiedTranpin(request.getNewTransPincode());
        customerProfileRepository.save(profile);
        sendOtp(profile, request.getChannelCode(), request.getPinType(), "Change Transaction Pin Verification OTP", request.isOtpRequired());
    }

    public void validateOldPin(CustomerProfile profile, String oldPin) {
        if (Objects.nonNull(profile.getCmpTranpin()) && !profile.getCmpTranpin().equals(oldPin)) {
            throw new OldPinIncorrectException();
        }
    }

    public Long sendOtp(CustomerProfile profile, String channelCode, String pinType, String subject, boolean isOtpRequired) {
        OTPRequest otpRequest = OTPRequest.builder()
                .porOrgacode(profile.getPorOrgacode())
                .channelCode(channelCode)
                .pinType(pinType)
                .email(profile.getCmpEmail())
                .phone(profile.getPadAdrsmobphone())
                .isOtpRequired(isOtpRequired)
                .username(profile.getCmpUserName())
                .subject(subject)
                .build();
        return notificationService.sendOtp(otpRequest);
    }

}
