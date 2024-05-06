package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositAccount {
    private String porOrgacode;
    private String mbmBkmsnumber;
    private String pcrCurrdesc;
    private String pcrCurrCode;
    private String pctCstycode;
    private String mbmBkmstitle;
    private BigDecimal mbmBkmsbalance;
    private String mbmBkmsopendate;
    private String AccountType;
}
