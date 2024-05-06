package com.mfsys.uco.controller;

import com.mfsys.comm.util.FunctionReturnDetail;
import com.mfsys.uco.UCOURI;
import com.mfsys.uco.dto.CashInTransactionRequest;
import com.mfsys.uco.dto.CashOutTransactionRequest;
import com.mfsys.uco.dto.Transaction.TransactionOtpRequestModel;
import com.mfsys.uco.dto.TransactionPinResponseModel;
import com.mfsys.uco.dto.webclientdto.AccountDetail;
import com.mfsys.uco.model.TransactionTrail;
import com.mfsys.uco.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(UCOURI.GET_DR_TRANSACTION_PIN)
    public TransactionPinResponseModel submitTransaction(@RequestBody TransactionOtpRequestModel transactionOtpRequestModel) {
        return transactionService.sendOtpAndValidateTranPin(transactionOtpRequestModel);
    }

    @PostMapping(UCOURI.SUBMIT_DR_TRANSACTION)
    public Map<String,Object> cashInTransaction(@RequestBody CashInTransactionRequest transactionRequest) {
        return transactionService.cashInTransaction(transactionRequest);
    }

    @PostMapping(UCOURI.SUBMIT_CR_TRANSACTION)
    public Map<String,Object> cashOutTransaction(@RequestBody CashOutTransactionRequest transactionRequest) {
        return transactionService.cashOutTransaction(transactionRequest);
    }

    @GetMapping(UCOURI.PENDING_CR_TRANSACTION)
    public List<TransactionTrail> getDepositAccounts(
            @RequestParam String porOrgacode,
            @RequestParam String mbmBkmsnumber) {
        return transactionService.fetchPendingCrTransactions(porOrgacode, mbmBkmsnumber);
    }

    @GetMapping(UCOURI.ACCOUNT_STATEMENT)
    public List<TransactionTrail> getAccountStatement(
            @RequestParam String porOrgacode,
            @RequestParam String mbmBkmsnumber) {
        return transactionService.fetchDepositAccountStatement(porOrgacode, mbmBkmsnumber);
    }
}

