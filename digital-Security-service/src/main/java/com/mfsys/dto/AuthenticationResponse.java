package com.mfsys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mfsys.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonProperty("name")
    String name;
    @JsonProperty("cmpCuststatus")
    String cmpCuststatus;
    @JsonProperty("cmpCustlastlogin")
    String cmpCustlastlogin;
    @JsonProperty("cmpCustcode")
    String cmpCustcode;
    @JsonProperty("userRole")
    Role userRole;
    @JsonProperty("jwtToken")
    String jwtToken;
    @JsonProperty("refreshToken")
    String refreshToken;
    @JsonProperty("isKycVerified")
    boolean isKycVerified;
}
