package com.mfsys.exception;


import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ERRCode;

public class SamePasswordException extends ApplicationException {
    public SamePasswordException() {
        super(null, ERRCode.SAME_PASSWORD, null);
    }
}
