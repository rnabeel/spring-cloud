package com.mfsys.uco.exception;


import com.mfsys.comm.exception.ApplicationException;
import com.mfsys.comm.exception.ERRCode;

import javax.swing.*;

public class OldPinIncorrectException extends ApplicationException {
    public OldPinIncorrectException() {
        super(null, ERRCode.OLD_PIN_INCORRECT, null);
    }
}
