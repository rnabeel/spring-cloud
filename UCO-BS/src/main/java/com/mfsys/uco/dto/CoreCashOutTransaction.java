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
public class CoreCashOutTransaction {
    private String porOrgacode;
    private String crMbmBkmsnumber;
    private BigDecimal sgtGntramtfc;
    private String crPcrCurrcode;
    private String otdTrancomment;
}
