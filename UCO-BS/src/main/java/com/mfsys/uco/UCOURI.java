package com.mfsys.uco;

public interface UCOURI {
    String VIEW_BALANCE = "/user/viewBalance";
    String ONBOARD_CUSTOMER = "/auth/user/authenticate/onboardCutomer";
    String FETCH_DEPOSITACCOUNTS = "/depositAccounts";
    String FETCH_ACCOUNT_STATEMENT = "/accountStatement";
    String FETCH_ACCOUNT_INQUIRY = "/accountInquiry";
    String GENERATE_TRANSACTIONS_REPORT = "/generateReport";
    String CREATE_TRAN_PIN = "/createTransactionPin";
    String VERIFY_TRAN_PIN = "/verifyTransactionPin";
    String CHANGE_TRAN_PIN = "/changeTransactionPin";
    String FETCH_LOGIIN_DATA = "/fetchlogindata";
    String FETCH_EXCHANGE_RATE = "/fetchExchangeRate";
    String GET_DR_TRANSACTION_PIN = "/sendDrTranOtpAndVerifyTranPin";
    String SUBMIT_DR_TRANSACTION = "/submitDrTransaction";
    String SUBMIT_CR_TRANSACTION = "/submitCrTransaction";
    String PENDING_CR_TRANSACTION = "/fetchPendingCredittransaction";
    String ACCOUNT_STATEMENT = "/fetchDepositAccountStatement";
    String BANKING_CASH_IN = "/deposit/transactions/uco/cash-in";
    String BANKING_CASH_OUT = "/deposit/transactions/uco/cash-out";
}
