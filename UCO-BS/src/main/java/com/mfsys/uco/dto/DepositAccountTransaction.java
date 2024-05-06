package com.mfsys.uco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositAccountTransaction {
    private String tranID;
    private String sgtGntrCreatedAt;
    private String sgtGntrNarration;
    private String sgtGntrvaluedate;
    private String deposit;
    private String withdrawal;
    private String status;
    private String sgtGntramt;
}
