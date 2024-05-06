package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyPinRequest {
        String channelCode;
        String pctCstycode;
        String porOrgacode;
        String cmpCustcode;
        String email;
        String obpPincode;
        String pinType;
}


