package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OTPRequest {
    private boolean isOtpRequired;
    private String email;
    private String phone;
    private String channelCode;
    private String porOrgacode;
    private String username;
    private String pinType;
    private String subject;
}
