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
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private String phone;
    private String name;
    private Role userRole;
    private String channelCode;
    private String porOrgacode;
    private boolean isOtpRequired;
}
