package com.mfsys.uco.model;

import com.mfsys.comm.util.FieldNameLength;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "BN_MS_UC_UCOACCOUNT")
@Table(name = "BN_MS_UA_UCOACCOUNT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UcoAccount {
    @EmbeddedId
    private AccountId id;

    @Column(name = "DMP_PRODCODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.DMP_PRODCODE)
    protected String dmpProdcode;

    @Column(name = "MBM_BKMSTITLE", nullable = false, updatable = true, columnDefinition = FieldNameLength.ACCOUNT_TITLE)
    protected String mbmBkmstitle;

    @Column(name = "PCR_CURRDESC", nullable=false, columnDefinition=FieldNameLength.DESCRIPTION_LONG)
    private String pcrCurrdesc;

    @Column(name = "CMP_CUSTCODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.CUSTOMER_CODE)
    protected String cmpCustcode;

    @Column(name = "PCR_CURRCODE", columnDefinition=FieldNameLength.PCR_CURRCODE)
    private String pcrCurrcode;

    @Column(name = "MBM_BKMSCLOSED", nullable = false, columnDefinition = FieldNameLength.BOOLEAN_BIT)
    protected boolean mbmBkmsclosed;

    @Column(name = "SGT_LASTTRANDATE", nullable = true, updatable = true, columnDefinition = FieldNameLength.DATE)
    protected LocalDate sgtLasttrandate;

    @Column(name = "MBM_BKMSOPENDATE", nullable = false, columnDefinition = FieldNameLength.DATE)
    protected LocalDate mbmBkmsopendate;

}
