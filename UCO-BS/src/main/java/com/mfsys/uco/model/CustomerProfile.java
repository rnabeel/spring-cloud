package com.mfsys.uco.model;

import com.mfsys.comm.util.FieldNameLength;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "BN_CS_MP_CUSTOMERPROFILE")
@Table(name = "BN_CS_MP_CUSTOMERPROFILE")
@Data
@Builder
@IdClass(CustomerProfileId.class)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfile {
    @Id
    @Column(name = "POR_ORGACODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.POR_ORGACODE)
    protected String porOrgacode;

    @Id
    @Column(name = "CMP_CUSTCODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.CUSTOMER_CODE)
    protected String cmpCustcode;

    @Column(name = "PIT_IDENVALUE", nullable = true, columnDefinition = FieldNameLength.CODE_20)
    protected String pitIdenvalue;


    @Column(name = "PIT_IDENCODE", nullable = true, columnDefinition = FieldNameLength.CODE_20)
    protected String pitIdencode;

    @Column(name = "PAD_ADRSMOBPHONE", nullable = true, columnDefinition = FieldNameLength.CODE_20)
    protected String padAdrsmobphone;

    @Column(name = "KYC_RENEWAL_DATE", nullable = true, columnDefinition = FieldNameLength.DATE)
    protected LocalDate kycRenewalDate;

    @Column(name = "CMP_EMAIL", nullable = true, columnDefinition = FieldNameLength.CODE_50)
    private String cmpEmail;

    @Column(name = "CMP_NAME", nullable = true, columnDefinition = FieldNameLength.CODE_50)
    private String cmpName;

    @Column(name = "CMP_USERNAME", nullable = true, columnDefinition = FieldNameLength.CODE_50)
    private String cmpUserName;

    @Column(name = "CMP_ISKYC_VERIFIED", nullable=false, columnDefinition=FieldNameLength.BOOLEAN_BIT)
    protected boolean cmpIsKycVerified = false;

    @Column(name = "CMP_TRAN_PIN", nullable=true, columnDefinition=FieldNameLength.PIN_VALUE)
    protected String cmpTranpin;

    @Column(name = "CMP_UNVERIFIED_TRAN_PIN", nullable=true, columnDefinition=FieldNameLength.PIN_VALUE)
    protected String cmpUnverifiedTranpin;

    @Column(name = "CMP_TRAN_PIN_VERIFIED", nullable=false, columnDefinition=FieldNameLength.BOOLEAN_BIT)
    protected boolean cmpTranpinVerfied = false;
}
