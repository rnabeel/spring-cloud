package com.mfsys.comm.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public interface TokenBypassURI {

    List<String> URIs = new ArrayList<String>(Arrays.asList("/security/auth/user/register",
            "security/auth/refreshtoken", "/security/api/generateInternalToken", "/security/auth/user/register",
            "security/auth/device", "security/realtime/location",
            "security/user/forgotPassword",
            "security/auth/userLog",
            "security/refreshtoken",
            "security/customer/register",
            "security/customer/register/checkid",
            "security/customer/register/checkaccountno",
            "/notification/otp/email",
            "/uco/auth/user/authenticate/onboardCutomer",
            "/uco/fetchlogindata"
    ));
}
