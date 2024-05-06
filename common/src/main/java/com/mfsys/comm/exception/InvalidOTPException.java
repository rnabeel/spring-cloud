package com.mfsys.comm.exception;


public class InvalidOTPException extends ApplicationException {
    public InvalidOTPException(String porOrgacode, Object... arguments) {
        super(porOrgacode, ERRCode.INVALID_OTP, arguments);
    }
}
