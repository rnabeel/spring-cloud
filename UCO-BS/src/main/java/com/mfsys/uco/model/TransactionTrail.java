package com.mfsys.uco.model;

import com.mfsys.comm.util.FieldNameLength;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "BN_MS_UA_UCOACCOUNTTRAIL")
@Table(name = "BN_MS_UA_UCOACCOUNTTRAIL")
public class TransactionTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "POR_ORGACODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.POR_ORGACODE)
    protected String porOrgacode;

    @Column(name = "DR_MBM_BKMSNUMBER", nullable = false, updatable = false, columnDefinition = FieldNameLength.ACCOUNT_NUMBER)
    protected String drMbmBkmsnumber;

    @Column(name = "CR_MBM_BKMSNUMBER", nullable = false, updatable = false, columnDefinition = FieldNameLength.ACCOUNT_NUMBER)
    protected String crMbmBkmsnumber;

    @Column(name = "DMP_PRODCODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.DMP_PRODCODE)
    protected String dmpProdcode;

    @Column(name = "DR_MBM_BKMSTITLE", nullable = false, updatable = true, columnDefinition = FieldNameLength.ACCOUNT_TITLE)
    protected String drmbmBkmstitle;

    @Column(name = "DR_PCR_CURRDESC", nullable=false, columnDefinition=FieldNameLength.DESCRIPTION_LONG)
    private String drpcrCurrdesc;

    @Column(name = "CMP_CUSTCODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.CUSTOMER_CODE)
    protected String cmpCustcode;

    @Column(name = "DR_PCR_CURRCODE", columnDefinition=FieldNameLength.PCR_CURRCODE)
    private String drPcrCurrcode;

    @Column(name = "CR_MBM_BKMSTITLE", nullable = false, updatable = true, columnDefinition = FieldNameLength.ACCOUNT_TITLE)
    protected String crMbmBkmstitle;

    @Column(name = "CR_PCR_CURRDESC", nullable=false, columnDefinition=FieldNameLength.DESCRIPTION_LONG)
    private String crPcrCurrdesc;

    @Column(name = "CR_PCR_CURRCODE", columnDefinition=FieldNameLength.PCR_CURRCODE)
    private String crPcrCurrcode;

    @Column(name = "SGT_SENTGNTRNUMBER", nullable = false, updatable = false, columnDefinition = FieldNameLength.CODE_500)
    protected String sgtSentGntrnumber;

    @Column(name = "DR_SGT_GNTRDATE", nullable = false, updatable = false, columnDefinition = FieldNameLength.DATE)
    protected LocalDate drSgtGntrdate;

    @Column(name = "CR_SGT_GNTRDATE", nullable = true, updatable = false, columnDefinition = FieldNameLength.DATE)
    protected LocalDate crSgtGntrdate;

    @Column(name = "SGT_GNTRAMT", nullable = false, updatable = false, columnDefinition = FieldNameLength.AMOUNT_REAL)
    protected BigDecimal sgtGntramt = BigDecimal.ZERO;

    @Column(name = "BAT_ACNTTRANSENT", nullable = false, columnDefinition = FieldNameLength.BOOLEAN_BIT)
    protected boolean batAcnttranSend;

    @Column(name = "BAT_ACNTTRANRECEIVED", nullable = true, columnDefinition = FieldNameLength.BOOLEAN_BIT)
    protected boolean batAcnttranReceived;

    @Column(name = "SGT_RECEIVEGNTRNUMBER", nullable = true, updatable = true, columnDefinition = FieldNameLength.CODE_500)
    protected String sgtReceiveGntrnumber;

}
