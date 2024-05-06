package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupStep3RequestModel {
    private String username;
    private String email;
    private String phone;
    private String name;
    private String address;
    private String identificationType;
    private String identificationNumber;
    private boolean isKycAdded;
    private String kycType;
    private String kycDocumentId1; // base64 encoded
    private String kycDocumentId2; // base64 encoded
    private String userRole;
    private String channelCode;
    private String porOrgacode;
}
