package com.mfsys.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResendOtpRequest {
    private String username;
    private String email;
    private String channelCode;
    private String porOrgacode;
    private String isOtpRequired;
    private String pinType;
}
