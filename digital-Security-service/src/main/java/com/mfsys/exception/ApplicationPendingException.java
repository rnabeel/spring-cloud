package com.mfsys.exception;


import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ERRCode;

public class ApplicationPendingException extends ApplicationException {
    public ApplicationPendingException() {
        super(null, ERRCode.APPLICATION_PENDING, null);
    }
}
