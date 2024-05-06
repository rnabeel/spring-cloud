package com.mfsys.comm.exception;


import java.text.MessageFormat;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    protected final String porOrgacode;
    protected final String errorCode;
    protected final Object[] arguments;
    protected final Object[] IMMUTABLE_ZERO_LEN_ARRAY = new Object[0];
    protected String errorDescription;

    public ApplicationException(String porOrgacode, ErrorMessage errorCode, Object... arguments) {
        this(porOrgacode, errorCode.getCode(), arguments);
        this.errorDescription = errorCode.getDescription();
    }

    public ApplicationException(String porOrgacode, String errorCode, Object... arguments) {
        this.porOrgacode = porOrgacode;
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    public ApplicationException(String porOrgacode, String errorCode, Throwable e) {
        super(errorCode, e);
        this.porOrgacode = porOrgacode;
        this.errorCode = errorCode;
        arguments = IMMUTABLE_ZERO_LEN_ARRAY;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        if (errorDescription != null) {
            return replacePlaceholders(errorDescription, arguments);
        }
        return null;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public String getPorOrgacode() {
        return this.porOrgacode;
    }

    private String replacePlaceholders(String message, Object[] arguments) {
        return MessageFormat.format(message, arguments);
    }

}
