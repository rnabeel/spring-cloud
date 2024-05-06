package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionPinRequest {
    private String newTransPincode;
    private String channelCode;
    private String pctCstycode;
    private String porOrgacode;
    private String cmpCustcode;
    private boolean isOtpRequired;
    private String pinType;
}


