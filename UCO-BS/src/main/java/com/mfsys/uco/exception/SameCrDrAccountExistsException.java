package com.mfsys.uco.exception;


import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ERRCode;

public class SameCrDrAccountExistsException extends ApplicationException {
    public SameCrDrAccountExistsException() {
        super(null, ERRCode.CR_DR_SAME_ACCOUNT, null);
    }
}
