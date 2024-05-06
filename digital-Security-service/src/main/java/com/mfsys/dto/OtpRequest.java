package com.mfsys.dto;

import com.mfsys.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
    private String username;
    private String email;
    private String phone;
    private Role userRole;
    private String channelCode;
    private String porOrgacode;
    private String pinType;
    private boolean isOtpRequired;
}
