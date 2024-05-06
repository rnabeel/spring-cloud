package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoreCashInTransaction {
    private String porOrgacode;
    private String drMbmBkmsnumber;
    private double sgtGntramtfc;
    private String drPcrCurrcode;
    private String otdTrancomment;
}
