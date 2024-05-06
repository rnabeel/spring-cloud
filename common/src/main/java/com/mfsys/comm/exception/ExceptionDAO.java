package com.mfsys.comm.exception;

public class ExceptionDAO {

    private String requestId;
    private Object[] exceptionArgs;


    public ExceptionDAO(String requestId, Object... exceptionArgs) {
        this.requestId = requestId;
        this.exceptionArgs = exceptionArgs;
    }


    public String getRequestId() {
        return requestId;
    }


    public Object[] getExceptionArgs() {
        return exceptionArgs;
    }


}
