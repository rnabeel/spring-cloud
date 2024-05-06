package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashInTransactionRequest {
    private String porOrgacode;
    private String pctCstycode;
    private String channelCode;
    private String cmpCustcode;
    private String drMbmBkmsnumber;
    private String drMbmBkmstitle;
    private String drPcrCurrcode;
    private String drPcrCurrdesc;
    private String crMbmBkmsnumber;
    private String crMbmBkmstitle;
    private String crPcrCurrcode;
    private String crPcrCurrdesc;
    private String sgtGntrnarration;
    private String dmpProdCode;
    private String transType;
    private String notificationId;
    private String transMode;
    private double sgtGntramtfc;
    private String otdTrancomment;
    private String obpPincode ;
    private String pinType ;
}
