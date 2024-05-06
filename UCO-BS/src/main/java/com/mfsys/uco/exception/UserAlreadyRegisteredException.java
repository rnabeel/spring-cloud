package com.mfsys.uco.exception;


import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ERRCode;

public class UserAlreadyRegisteredException extends ApplicationException {
    public UserAlreadyRegisteredException(String porOrgacode, Object... arguments) {
        super(porOrgacode, ERRCode.USER_ALREADY_REGISTERED, arguments);
    }
}
