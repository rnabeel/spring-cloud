package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionPinRequestModel {
    private String porOrgacode;
    private String pctCstycode;
    private String channelCode;
    private String cmpCustcode;
    private String drMbmBkmsnumber;
    private String crMbmBkmsnumber;
    private String sgtGntrnarration;
    private double sgtGntramtfc;
    private String drCurrencyCode;
    private String crCurrencyCode;
    private String exchnageRate;
}
