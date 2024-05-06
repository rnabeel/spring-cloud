package com.mfsys.exception;


import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ERRCode;

public class PasswordIncorrectException extends ApplicationException {
    public PasswordIncorrectException(String porOrgacode, Object... arguments) {
        super(porOrgacode, ERRCode.INCORRECT_PASSWORD, arguments);
    }
}
