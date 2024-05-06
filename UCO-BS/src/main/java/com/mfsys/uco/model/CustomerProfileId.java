package com.mfsys.uco.model;

import java.io.Serializable;

public class CustomerProfileId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String porOrgacode;
    private String cmpCustcode;
    
    public String getPorOrgacode() {
		return porOrgacode;
	}

	public void setPorOrgacode(String porOrgacode) {
		this.porOrgacode = porOrgacode;
	}

	public String getCmpCustcode() {
		return cmpCustcode;
	}

	public void setCmpCustcode(String cmpCustcode) {
		this.cmpCustcode = cmpCustcode;
	}

	public CustomerProfileId() {
    }

    public CustomerProfileId(String porOrgacode, String cmpCustcode) {
        this.porOrgacode = porOrgacode;
        this.cmpCustcode = cmpCustcode;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmpCustcode == null) ? 0 : cmpCustcode.hashCode());
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
		CustomerProfileId other = (CustomerProfileId) obj;
		if (cmpCustcode == null) {
			if (other.cmpCustcode != null)
				return false;
		} else if (!cmpCustcode.equals(other.cmpCustcode))
			return false;
		if (porOrgacode == null) {
			if (other.porOrgacode != null)
				return false;
		} else if (!porOrgacode.equals(other.porOrgacode))
			return false;
		return true;
	}    
}
