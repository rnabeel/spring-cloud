package com.mfsys.uco.dto.webclientdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetail {
    protected String porOrgacode;
    protected String mbmBkmsnumber;
    protected String mbmBkmstitle;
    protected String dmpProddesc;
    protected String plcLocadesc;
    protected String pcrCurrcode;
    protected String pcrCurrdesc;
    protected String cmpCustcode;
    protected boolean mbmBkmsclosed;
    protected String pctCstycode;
    protected BigDecimal mbmBkmsbalance;
    protected Map<String, BigDecimal> charges = Map.of();
    protected String dmpProdcode;
    protected boolean cmpBlacklisted;
    protected String plcLocacode;
    protected boolean mbmNotificationService;
    protected String dmpCredittype;
    protected BigDecimal perEratrateact;
    protected LocalDate kycRenewalDate;
    protected String padAdrsmobphone;
    protected boolean btaRolloverSpecialRate;
    private String pasAcstcode;
    private BigDecimal btaBookingamount;
    private BigDecimal bdaDpacblockamt;
    private boolean bdaDpacblocked;
    private String pbdBankname;
    private String pbbBranchname;
    private String pbbBranchcountry;
    private String pbbBranchcity;
    private String pcaGlaccode;
    private String accJointStatus;
    private String accAttortype;

}
