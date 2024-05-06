package com.mfsys.uco.dto.Transaction;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransactionOtpRequestModel {
    private String porOrgacode;
    private String pctCstycode;
    private String channelCode;
    private String cmpCustcode;
    private String email;
    private String pinType;
    private String transPincode;
    private boolean isOtpRequired;
}
