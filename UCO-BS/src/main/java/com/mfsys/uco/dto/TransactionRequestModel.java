package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestModel {
    private String porOrgacode;
    private String pctCstycode;
    private String channelCode;
    private String cmpCustcode;
    private String obpPincode;
    private String otdTrancomment;
    private String otdTranrequestid;
}
