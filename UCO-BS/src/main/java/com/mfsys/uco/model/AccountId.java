package com.mfsys.uco.model;

import com.mfsys.comm.util.FieldNameLength;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AccountId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "POR_ORGACODE", nullable = false, updatable = false, columnDefinition = FieldNameLength.POR_ORGACODE)
    protected String porOrgacode;

    @Column(name = "MBM_BKMSNUMBER", nullable = false, updatable = false, columnDefinition = FieldNameLength.ACCOUNT_NUMBER)
    protected String mbmBkmsnumber;


    public AccountId(String porOrgacode, String mbmBkmsnumber) {
        this.porOrgacode = porOrgacode;
        this.mbmBkmsnumber = mbmBkmsnumber;
    }

    public AccountId() {

    }

    public String getPorOrgacode() {
        return porOrgacode;
    }

    public void setPorOrgacode(String porOrgacode) {
        this.porOrgacode = porOrgacode;
    }

    public String getMbmBkmsnumber() {
        return mbmBkmsnumber;
    }

    public void setMbmBkmsnumber(String mbmBkmsnumber) {
        this.mbmBkmsnumber = mbmBkmsnumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mbmBkmsnumber == null) ? 0 : mbmBkmsnumber.hashCode());
        result = prime * result + ((porOrgacode == null) ? 0 : porOrgacode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountId other = (AccountId) obj;
        if (mbmBkmsnumber == null) {
            if (other.mbmBkmsnumber != null)
                return false;
        } else if (!mbmBkmsnumber.equals(other.mbmBkmsnumber))
            return false;
        if (porOrgacode == null) {
            return other.porOrgacode == null;
        } else return porOrgacode.equals(other.porOrgacode);
    }


}
