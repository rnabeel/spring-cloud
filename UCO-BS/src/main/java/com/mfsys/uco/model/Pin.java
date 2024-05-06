package com.mfsys.uco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mfsys.comm.util.FieldNameLength;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDateTime;

@Entity(name = "DG_PN_PIN")
@Table(name = "DG_PN_PIN")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PINSERIAL", nullable = false, updatable = false, columnDefinition = FieldNameLength.BIGINT)
    private Long pinserial;

    @Column(name = "POR_ORGACODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.POR_ORGACODE)
    private String porOrgacode;

    @Column(name = "username", nullable = false, updatable = false, columnDefinition = FieldNameLength.USER_NAME)
    private String userName;

    @Column(name = "CHANNEL_CODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.CHANNEL_CODE)
    private String channelCode;

    @Column(name = "PINTYPE", nullable = false, updatable = false, columnDefinition = FieldNameLength.CODE_10)
    private String pintype;

    @Column(name = "PINLENGTH", nullable = false, updatable = false, columnDefinition = FieldNameLength.PIN_LENGTH)
    private int pinlength;

    @Column(name = "PINCODE", nullable = false, updatable = true, columnDefinition = FieldNameLength.PIN_VALUE)
    private String pincode;

    @Column(name = "PIN_CREATEDATE", nullable = false, updatable = true, columnDefinition = FieldNameLength.DATETIME)
    private LocalDateTime pinCreatedate;

    @Column(name = "PIN_EXPIRYDATE", nullable = false, updatable = true, columnDefinition = FieldNameLength.DATETIME)
    private LocalDateTime pinExpirydate;

    @Column(name = "PINSTATUS", nullable = false, updatable = true, columnDefinition = FieldNameLength.CODE_20)
    private String pinstatus;

    @Column(name = "PIN_VALIDATIONDATE", nullable = true, updatable = true, columnDefinition = FieldNameLength.DATETIME)
    private LocalDateTime pinValidationdate;

    @JsonIgnore
    @Column(name = "Version", nullable = false, updatable = true, columnDefinition = FieldNameLength.INT)
    @Version
    private int version;

}
