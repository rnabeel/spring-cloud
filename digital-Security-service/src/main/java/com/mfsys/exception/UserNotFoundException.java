package com.mfsys.exception;


import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ERRCode;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String porOrgacode, Object... arguments) {
        super(porOrgacode, ERRCode.USER_NOT_FOUND, arguments);
    }
}
