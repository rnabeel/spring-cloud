package com.mfsys.uco.exception;


import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ERRCode;

public class AccountDoesntExistsException extends ApplicationException {
    public AccountDoesntExistsException() {
        super(null, ERRCode.ACCOUNT_NOT_FOUND, null);
    }
}
