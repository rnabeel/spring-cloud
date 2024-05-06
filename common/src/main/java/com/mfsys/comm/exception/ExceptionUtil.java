package com.mfsys.comm.exception;

import org.slf4j.MDC;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    public static final ExceptionDAO toExceptionDAO(Throwable e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        String err = errors.toString();
        return new ExceptionDAO("restquestId={} , stacktrace={}", MDC.get("request_id"), err);
    }

}
