package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryRequest {
    private String porOrgacode;
    private String mbmBkmsnumber;
    private String sgtGntrvaluedatefrom;
    private String sgtGntrvaluedateto;
    private String type; // Expected to be "pdf"
}
