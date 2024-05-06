package com.mfsys.comm.exception;

public class ResourceNotFoundException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String porOrgacode, ErrorMessage errorCode, Object... arguments) {
        super(porOrgacode, errorCode, arguments);
    }

}
