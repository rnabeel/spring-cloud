package com.mfsys.exception;


import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ERRCode;

public class OldPasswordNotAllowedException extends ApplicationException {
    public OldPasswordNotAllowedException(String porOrgacode, Object... arguments) {
        super(porOrgacode, ERRCode.OLD_PASSWORD, arguments);
    }
}
